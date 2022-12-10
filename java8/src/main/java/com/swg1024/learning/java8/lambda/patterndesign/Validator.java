package com.swg1024.learning.java8.lambda.patterndesign;

/**
 *  针对策略模式，使用lambda表达式
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月10日 23:31
 */
public class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy v) {
        this.strategy = v;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }

    public static void main(String []args) {
        Validator upperValidator = new Validator(s -> s.matches("[A-Z]+"));
        System.out.println(upperValidator.validate("asdf"));
    }

}
