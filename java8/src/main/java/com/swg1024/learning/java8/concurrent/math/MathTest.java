package com.swg1024.learning.java8.concurrent.math;

import org.junit.Test;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 16:58
 */
public class MathTest {

    @Test
    public void test1() {
        Math.addExact(1, 2);
        Math.subtractExact(1, 2);
        Math.multiplyExact(1, 2);
        Math.toIntExact(1999L);
        System.out.println(Math.toDegrees(22.241324));

        System.out.println(Math.floorMod(5, 3));
    }
}
