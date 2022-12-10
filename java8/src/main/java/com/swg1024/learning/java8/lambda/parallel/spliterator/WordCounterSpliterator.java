package com.swg1024.learning.java8.lambda.parallel.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 *
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月08日 21:31
 */
public class WordCounterSpliterator implements Spliterator<Character> {

    private final String string;

    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        // 处理当前字符
        action.accept(string.charAt(currentChar++));
        // 如果还有字符要处理，则返回true
        return currentChar < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if (currentSize < 10) {
            // 返回null表示要解析的String已经足够小，可以顺序处理
            return null;
        }
        // 将试探拆分位置设定为要解析的string的中间
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            // 让拆分位置前进直到下一个空格
            if (Character.isWhitespace(string.charAt(splitPos))) {
                // 创建一个新WordCounterSpliterator来解析String从开始到拆分位置的部分
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
