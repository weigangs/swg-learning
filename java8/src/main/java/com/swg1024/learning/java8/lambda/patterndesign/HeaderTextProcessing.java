package com.swg1024.learning.java8.lambda.patterndesign;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 *
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月11日 17:05
 */
public class HeaderTextProcessing extends ProcessingObject<String> {



    @Override
    protected String handleWork(String input) {
        return "From Raoul, Mario and Alan: " + input;
    }

    @Test
    public void test() {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2);
        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);
    }

    @Test
    public void test2() {
        UnaryOperator<String> headerProcessing = text -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellHeckerProcessing = text -> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellHeckerProcessing);
        String result = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result);
    }
}

class SpellCheckerProcessing extends ProcessingObject<String> {

    @Override
    protected String handleWork(String input) {
        return input.replaceAll("labda", "lambda");
    }
}
