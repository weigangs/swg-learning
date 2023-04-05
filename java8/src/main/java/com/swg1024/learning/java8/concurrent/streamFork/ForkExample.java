package com.swg1024.learning.java8.concurrent.streamFork;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ForkExample {

    public static void main(String[] args) throws Exception {
        // Set up the forks
        StreamForker<String> forker = new StreamForker<>();
        StreamForker.Memento<String> mr1 = forker.addFork(
                s -> s.map(String::toUpperCase).collect(Collectors.joining()));
        StreamForker.Memento<Integer> mr2 = forker.addFork(
                s -> s.mapToInt(Integer::valueOf).sum());
        StreamForker.Memento<Map<Integer, Long>> mr3 = forker.addFork(
                s -> s.collect(Collectors.groupingBy(String::length, Collectors.counting())));
        StreamForker.Memento<Optional<String>> mr4 = forker.addFork(
                s -> s.skip(10).findFirst());

        // Fork an existing stream 4 times
        Stream<String> p = IntStream.range(0, 100).mapToObj(Integer::toString);
        ForkedStreamResults rs = forker.fork(p);

        // Wait for all forks to complete
        rs.all().join();

        // Get the results of each fork
        CompletableFuture<String> r1 = rs.get(mr1);
        CompletableFuture<Integer> r2 = rs.get(mr2);
        CompletableFuture<Map<Integer, Long>> r3 = rs.get(mr3);
        CompletableFuture<Optional<String>> r4 = rs.get(mr4);
        System.out.println(r1.get());
        System.out.println(r2.get());
        System.out.println(r3.get());
        System.out.println(r4.get());
    }

    /**
     * A forker of streams
     *
     * @param <T> the type of elements output from the forked stream.
     */
    public static class StreamForker<T> {

        /**
         * A memento to a fork
         *
         * @param <R> the type of result of the fork
         */
        static class Memento<R> {
        }

        private Map<Memento<?>, Function<Stream<T>, ?>> forks = new HashMap<>();

        /**
         * Add a fork
         *
         * @param f the fork function to apply to a forked stream. The stream is
         * forked, and the function is applied to that forked stream to produce
         * a result.
         * @param <R> the type of result of the fork
         * @return a typed memento associated with the fork that can be used to
         * obtain the result returned by the fork function
         */
        public <R> Memento<R> addFork(Function<Stream<T>, R> f) {
            Memento<R> m = new Memento<>();
            forks.put(m, f);
            return m;
        }

        /**
         * Fork a stream
         *
         * <p>The stream will be forked N times where N is the number of forks
         * added.
         *
         * @param s the stream to fork
         * @return the results of forking
         */
        public ForkedStreamResults fork(Stream<T> s) {
            // @@@ Obtain the spliterator from the stream so as to
            // get the characteristics that can be passed to the
            // LinkedBlockingQueueSpliterator, then re-create the
            // to-be-forked stream from that Spliterator

            ForkingStreamConsumer<T> consumer = build();
            try {
                // @@@ If the stream is parallel then the encounter order,
                // if any, will not be preserved, in addition the parallel
                // execution will compete with the execution of the forked
                // streams
                s.sequential().forEach(consumer);
            }
            finally {
                consumer.finish();
            }
            return consumer;
        }

        ForkingStreamConsumer<T> build() {
            List<LinkedBlockingQueue<T>> queues = new ArrayList<>();
            Map<Memento<?>, CompletableFuture<?>> actions = new HashMap<>();
            for (Map.Entry<Memento<?>, Function<Stream<T>, ?>> e : forks.entrySet()) {
                LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();
                queues.add(queue);

                // Forked representation of the stream
                Stream<T> source = StreamSupport.stream(
                        new LinkedBlockingQueueSpliterator<>(queue), false);
                Function<Stream<T>, ?> f = e.getValue();
                CompletableFuture<?> action = CompletableFuture.supplyAsync(
                        () -> f.apply(source));
                actions.put(e.getKey(), action);
            }

            return new ForkingStreamConsumer<>(queues, actions);
        }
    }

    /**
     * The results of forking a stream
     */
    public static interface ForkedStreamResults {
        /**
         * @return a completable future encapsulating the futures of all forks
         * that is completed when all forks complete.
         */
        public CompletableFuture<Void> all();

        /**
         * Get the completable future encapsulating the result of a fork
         * @param m the memento associated with the fork
         * @param <R> the type of results of the fork
         * @return the completable future encapsulating the result of the fork
         */
        public <R> CompletableFuture<R> get(StreamForker.Memento<R> m);
    }

    static class ForkingStreamConsumer<T> implements Consumer<T>, ForkedStreamResults {
        // Object element marking the end of the stream
        static final Object SENTINAL = new Object();

        private final List<LinkedBlockingQueue<T>> queues;
        private final Map<StreamForker.Memento<?>, CompletableFuture<?>> actions;

        ForkingStreamConsumer(List<LinkedBlockingQueue<T>> queues, Map<StreamForker.Memento<?>,
                CompletableFuture<?>> actions) {
            this.queues = queues;
            this.actions = actions;
        }

        @Override
        public void accept(T t) {
            // @@@ Buffering issues, can barf if queue is full
            // i.e. producer is faster than consumer
            queues.forEach(q -> q.add(t));
        }

        @Override
        public CompletableFuture<Void> all() {
            return CompletableFuture.allOf(
                    actions.values().stream().toArray(CompletableFuture[]::new));
        }

        @Override
        @SuppressWarnings("unchecked")
        public <R> CompletableFuture<R> get(StreamForker.Memento<R> m) {
            return (CompletableFuture<R>) actions.get(m);
        }

        @SuppressWarnings("unchecked")
        void finish() {
            accept((T) SENTINAL);
        }
    }

    static class LinkedBlockingQueueSpliterator<T> implements Spliterator<T> {
        private final LinkedBlockingQueue<T> q;

        private boolean finished;

        LinkedBlockingQueueSpliterator(LinkedBlockingQueue<T> q) {
            this.q = q;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (!finished) {
                T t;
                while (true) {
                    try {
                        t = q.take();
                        break;
                    }
                    catch (InterruptedException e) {
                    }
                }

                if (t != ForkingStreamConsumer.SENTINAL) {
                    action.accept(t);
                    return true;
                }

                finished = true;
            }
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            // @@@ Support limited splitting, using buffering with say
            // q.drainTo
            return null;
        }

        @Override
        public long estimateSize() {
            // @@@ Support size if known by forking stream
            return 0;
        }

        @Override
        public int characteristics() {
            // @@@ Inherit characters from forking stream
            return 0;
        }
    }
}
