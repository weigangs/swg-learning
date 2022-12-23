package com.swg1024.learning.java8.lambda.lambdapractice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;

import static java.util.Comparator.comparing;

/**
 *
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月11日 17:23
 */
@AllArgsConstructor
@Getter
public class Point {

    public final static Comparator<Point> compareByXAndThenY = comparing(Point::getX).thenComparing(Point::getY);

    private final int x;
    private final int y;

    public Point moveRightBy(int x) {
        return new Point(this.x + x, this.y);
    }

}
