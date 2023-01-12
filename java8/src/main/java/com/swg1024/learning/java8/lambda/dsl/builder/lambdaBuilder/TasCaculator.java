package com.swg1024.learning.java8.lambda.dsl.builder.lambdaBuilder;

import com.swg1024.learning.java8.lambda.dsl.entity.Order;

import java.util.function.DoubleUnaryOperator;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年01月12日 23:23
 */
public class TasCaculator {
    private DoubleUnaryOperator taxFunction = d -> d;

    public TasCaculator with(DoubleUnaryOperator f) {
        taxFunction.andThen(f);
        return this;
    }

    public double caculate(Order order) {
        return taxFunction.applyAsDouble(order.getValue());
    }
}
