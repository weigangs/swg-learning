package com.swg1024.learning.java8.lambda.patterndesign;

/**
 *
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月10日 23:29
 */
public class IsAllLowerCase implements ValidationStrategy {
    @Override
    public boolean execute(String e) {

        return e.matches("[a-z]+");
    }
}
