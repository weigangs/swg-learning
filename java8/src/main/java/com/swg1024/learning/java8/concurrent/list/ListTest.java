package com.swg1024.learning.java8.concurrent.list;

import java.util.Arrays;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 16:49
 */
public class ListTest {

    public void test1() {
        int [] evennumbers = new int[10];
        Arrays.setAll(evennumbers, i -> i * 2);
        Arrays.parallelSetAll(evennumbers, i -> i * 2);
        Arrays.fill(evennumbers, 1);
        Arrays.parallelPrefix(evennumbers, (a , b) -> a + b);
    }
}
