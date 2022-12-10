package com.swg1024.learning.java8.lambda.parallel.spliterator;

import lombok.AllArgsConstructor;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月08日 21:07
 */
@AllArgsConstructor
public class WordCounter {

    private final int counter;
    private final boolean lastSpace;

    public WordCounter accumulate(Character c) {
        // 和迭代方法一样，accumulate方法一个一个遍历Character
        if (Character.isWhitespace(c)) {

            return lastSpace ? this : new WordCounter(counter, true);
        }
        else {
            // 上一个字符是空格，而当前遍历地字符不是空格时，将单词计数器加一
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    public WordCounter combine(WordCounter wordCounter) {
        // 合并两个WordCounter，把其计数器加起来
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    public int getCounter() {
        return counter;
    }

    private int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }

    public static void main(String []args) {
        final String SEQUENCE = " Nel mezzo del cammin dinostra vita " +
                "mi  ritrovai in una  selvaoscura" +
                " che la   dritta via era smarrita ";
        Stream<Character> stream = IntStream.range(0, SEQUENCE.length()).mapToObj(SEQUENCE::charAt);
        System.out.println(new WordCounter(0, false).countWords(stream.parallel()));
    }
}
