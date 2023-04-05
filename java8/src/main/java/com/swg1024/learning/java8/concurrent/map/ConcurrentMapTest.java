package com.swg1024.learning.java8.concurrent.map;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 16:41
 */
public class ConcurrentMapTest {

    public void test1() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        // parallelismThreshold 表示map中元素达到多少时执行并发操作，Long.MAX_VALUE 表示不执行并发操作，
        // 此处1，表示并发， 并且并发使用的是一个通用线程池
        Optional<Integer> maxValue =
                Optional.of(map.reduceValues(1, Integer::max));
        // 用该方法替换size（）
        long count = map.mappingCount();
    }
}
