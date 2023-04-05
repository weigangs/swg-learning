package com.swg1024.learning.java8.concurrent.number;

import java.util.function.Predicate;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 16:55
 */
public class NumberTest {

    public void test1() {
        Predicate<Integer> predicate = x -> x > 1;
        Boolean.logicalAnd(predicate.test(1), predicate.test(2));
    }
}
