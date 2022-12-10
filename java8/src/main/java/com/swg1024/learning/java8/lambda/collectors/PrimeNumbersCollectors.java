package com.swg1024.learning.java8.lambda.collectors;

import com.swg1024.learning.java8.lambda.parallel.forJoinPool.ForkJoinSumCalculator;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collectors.partitioningBy;

/**
 * 质数收集器
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月06日 20:32
 *
 */
public class PrimeNumbersCollectors implements Collector<Integer, Map<Boolean, List<Integer>>,  Map<Boolean, List<Integer>>> {
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>(){{
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate)
                -> acc.get(isPrime(acc.get(true), candidate)).add(candidate);
    }

    /**
     * 并发后多线程结果合并器， 此处不会用到， 应为结果要有序
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    /**
     * 这个收集器是IDENTITY_FINISH，但既不是UNORDERED也不是CONCURRENT,因为质数是按顺序发现的
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
    }

    /**
     * 是否是质数
     * @param primes 质数列表 （有序）
     * @param candidate 候选值
     * @return boolean
     */
    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return primes.stream().takeWhile(i -> i <= candidateRoot).noneMatch(p -> candidate % p == 0);
    }


    @Test
    public void test() {
        System.out.println(IntStream.rangeClosed(2, 100).boxed().collect(new PrimeNumbersCollectors()));
    }

    @Test
    public void testPerformance() {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++ ) {
            long start = System.nanoTime();
            // 运行测试10次
            partitionPrimes(1_000_000);
            // 将前一百万个自然收按质数和非质数分区
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                // 检查这个执行是否是最快的一个
                fastest = duration;
            }
            System.out.println("Fastest execution done in " + fastest + " msecs");
        }
    }

    @Test
    public void testPerformance2() {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++ ) {
            long start = System.nanoTime();
            // 运行测试10次
            IntStream.rangeClosed(2, 1_000_000).boxed().collect(new PrimeNumbersCollectors());
            // 将前一百万个自然收按质数和非质数分区
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                // 检查这个执行是否是最快的一个
                fastest = duration;
            }
            System.out.println("Fastest execution done in " + fastest + " msecs");
        }
    }

    @Test
    public void testPerformance3() {
        long fastest = Long.MAX_VALUE;
        Map<Boolean, List<Integer>> map = null;
        for (int i = 0; i < 10; i++ ) {
            long start = System.nanoTime();
            // 运行测试10次

            IntStream.rangeClosed(2, 1_000_000).boxed().collect(
                    () -> new HashMap<Boolean, List<Integer>>(){{
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }},
                    (Map<Boolean, List<Integer>> acc,Integer candidate) ->
                            acc.get(isPrime(acc.get(true), candidate)).add(candidate),
                    (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
                        map1.get(true).addAll(map2.get(true));
                        map1.get(false).addAll(map2.get(false));
                    });
            // 将前一百万个自然收按质数和非质数分区
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                // 检查这个执行是否是最快的一个
                fastest = duration;
            }
            System.out.println("Fastest execution done in " + fastest + " msecs");
        }
    }

    @Test
    public void testPerformance4() {
        long fastest = Long.MAX_VALUE;
        Map<Boolean, List<Integer>> map = null;
        for (int i = 0; i < 10; i++ ) {
            long start = System.nanoTime();
            // 运行测试10次

            long [] numbers = LongStream.rangeClosed(1, 1_000_000).toArray();
            ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
            System.out.println(new ForkJoinPool().invoke(task));
            // 将前一百万个自然收按质数和非质数分区
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                // 检查这个执行是否是最快的一个
                fastest = duration;
            }
            System.out.println("Fastest execution done in " + fastest + " msecs");
        }
    }

    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> oldIsPrime(candidate)));
    }

    public boolean oldIsPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % 2 == 0);
    }

    @Test
    public void testCore() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
