package com.swg1024.learning.java8.publishersub;

import com.swg1024.learning.java8.publishersub.entity.ArithmeticCell;
import com.swg1024.learning.java8.publishersub.entity.SimpleCell;
import org.junit.Test;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年02月25日 9:53
 */
public class PublisherSubTest {

    @Test
    public void test1() {
        SimpleCell c3 = new SimpleCell("C3");

        SimpleCell c2 = new SimpleCell("C2");

        SimpleCell c1 = new SimpleCell("C1");

        c1.subscribe(c3);
        c1.onNext(10);
        c2.onNext(20);
    }

    @Test
    public void test2() {
        ArithmeticCell c3 = new ArithmeticCell("C3");
        SimpleCell c2 = new SimpleCell("C2");
        SimpleCell c1 = new SimpleCell("C1");

        c1.subscribe(c3::setLeft);
        c2.subscribe(c3::setRight);

        c1.onNext(10);
        c2.onNext(20);
        c1.onNext(15);
    }
}
