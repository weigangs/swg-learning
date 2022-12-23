package com.swg1024.learning.java8.lambda.lambdapractice;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月11日 17:25
 */
public class PointTest {

    @Test
    public void testMoveRightBy() throws Exception {
        Point p1 = new Point(5, 5);
        Point p2 = p1.moveRightBy(10);
        assertEquals(15, p2.getX());
        assertEquals(5, p2.getY());
    }
}
