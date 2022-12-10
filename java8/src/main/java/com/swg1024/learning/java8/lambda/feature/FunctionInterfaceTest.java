package com.swg1024.learning.java8.lambda.feature;

import org.apache.commons.lang3.function.TriFunction;
import org.junit.Test;

import java.util.function.Function;


/**
 *
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年11月27日 17:27
 */
public class FunctionInterfaceTest {

    @Test
    public  void test() {
        System.out.println(((Function<Integer, Integer>)(i-> i +1)).andThen(integer -> integer * 3).andThen(i->i+10)
                .compose((Integer i)-> i + 10).apply(1));


        System.out.println(((Function<String, String>)Letter::addHeader).andThen(Letter::addFooter)
                .andThen(Letter::checkSpelling).apply("happy birthday!"));


        // 梯形面积计算 m = {f(a) * a + f(b) * b} * (b-a) * 1/2;  f(x) = x + C; C = 10.0;
        System.out.println(((TriFunction<Function<Double, Double>, Double, Double, Double>)(f, a, b) ->
                (f.apply(a) + f.apply(b)) * (b-a) / 2.0).apply(x-> x+10.0, 3.0, 7.0));
    }


}



class Letter {
    public static String addHeader(String text) {
        return "From raoul, Mario and Alan: " + text;
    }

    public static String addFooter(String text) {
        return text + " Kind regards";
    }

    public static String checkSpelling(String text) {
        return text.replaceAll("labda", "lambda");
    }
}


