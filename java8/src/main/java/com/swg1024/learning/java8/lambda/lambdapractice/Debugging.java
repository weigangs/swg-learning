package com.swg1024.learning.java8.lambda.lambdapractice;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月12日 22:11
 */
public class Debugging {

    public static void main(String [] args) {
        List<Point> points = Arrays.asList(new Point(12, 2), null);
        points.stream().map(p -> p.getX()).forEach(System.out::println);
    }

    @Test
    public void debugByLog() {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);
        numbers.stream().map(x -> x + 17).filter(x -> x % 2 == 0).limit(3).peek(System.out::println).collect(Collectors.toList());

    }
}
