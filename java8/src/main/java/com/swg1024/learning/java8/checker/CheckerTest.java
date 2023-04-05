package com.swg1024.learning.java8.checker;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月29日 21:38
 */
public class CheckerTest {


    @Test
    public void test1() {
        // java8 支持变量参数，泛型类型上添加注解，以及支持重复注解（例如一个类上可重复添加同一类型注解）
        @NonNull String name = null;
        System.out.println(name);
    }

    public String getName() {
        return null;
    }
}
