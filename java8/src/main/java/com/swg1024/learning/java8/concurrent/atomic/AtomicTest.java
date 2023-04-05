package com.swg1024.learning.java8.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 16:26
 */
public class AtomicTest {

    /**
     * 以下都是原子操作，适合多线程
     */
    private void test1() {
        AtomicInteger integer = new AtomicInteger(0);
        integer.getAndUpdate(i -> i + 1);
        integer.updateAndGet(i -> i + 1);
        integer.getAndAccumulate(5, (prev, cur) -> prev + cur);

    }

    /**
     * 多线程下，频繁更新，推荐使用以下类
     */
    private void test2() {
        LongAdder adder = new LongAdder();
        adder.add(10);
        // 在多个线程下累加后
        // 在某一时刻获取值
        Long sum = adder.sum();

        LongAccumulator acc = new LongAccumulator(Long::sum, 0);
        acc.accumulate(10);

        Long result = acc.get();
    }
}
