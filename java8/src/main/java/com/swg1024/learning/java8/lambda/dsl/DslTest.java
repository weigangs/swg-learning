package com.swg1024.learning.java8.lambda.dsl;

import com.swg1024.learning.java8.lambda.dsl.builder.MethodChainingOrderBuilder;
import com.swg1024.learning.java8.lambda.dsl.builder.lambdaBuilder.LambdaOrderBuilder;
import com.swg1024.learning.java8.lambda.dsl.builder.lambdaBuilder.TasCaculator;
import com.swg1024.learning.java8.lambda.dsl.entity.Order;
import org.junit.Test;

import static com.swg1024.learning.java8.lambda.dsl.builder.lambdaBuilder.LambdaOrderBuilder.buy;
import static com.swg1024.learning.java8.lambda.dsl.builder.lambdaBuilder.LambdaOrderBuilder.sell;


/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年01月11日 23:23
 */
public class DslTest {

    @Test
    public void test() {
        Order order = MethodChainingOrderBuilder.forCustomer("nicholas").buy(5).stock("TSL").on("上证").at(1006.32)
                .sell(3).stock("TSL").on("上证").at(1200.32).end();
        System.out.println(order);
    }

    @Test
    public void test1() {
        Order order = LambdaOrderBuilder.forCustomer("nicholas", buy(t-> t.at(12.12).quantity(100).stock("TSL").on("上证")), sell(t-> t.stock("TSL").on("上证").quantity(50).at(20.12)));
        /**
         * regional tax = value * 1.1
         * general tax = value * 1.3
         * surcharge tax = value * 1.05
         */
        TasCaculator caculator = new TasCaculator();
        caculator.with(value -> value * 1.1).with(value -> value * 1.05).caculate(order);
    }
}
