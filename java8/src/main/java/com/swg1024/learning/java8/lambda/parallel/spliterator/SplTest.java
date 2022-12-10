package com.swg1024.learning.java8.lambda.parallel.spliterator;

import org.junit.Test;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月08日 21:56
 */
public class SplTest {

    public int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }

    @Test
    public void test1() {
        final String SEQUENCE = " Nel mezzo del cammin dinostra vita " +
                "mi  ritrovai in una  selvaoscura" +
                " che la   dritta via era smarrita ";
        Stream<Character> stream = IntStream.range(0, SEQUENCE.length()).mapToObj(SEQUENCE::charAt);
        System.out.println(countWords(stream));
    }

    @Test
    public void test2() {
        final String SEQUENCE = " Nel mezzo del cammin dinostra vita " +
                "mi  ritrovai in una  selvaoscura" +
                " che la   dritta via era smarrita ";
        Stream<Character> stream = IntStream.range(0, SEQUENCE.length()).mapToObj(SEQUENCE::charAt);
        System.out.println(countWords(stream.parallel()));
    }

    @Test
    public void test3() {
        final String SEQUENCE = " Nel mezzo del cammin dinostra vita " +
                "mi  ritrovai in una  selvaoscura" +
                " che la   dritta via era smarrita ";
        Spliterator<Character> spliterator = new WordCounterSpliterator(SEQUENCE);
        System.out.println(countWords(StreamSupport.stream(spliterator, true)));
    }


}
