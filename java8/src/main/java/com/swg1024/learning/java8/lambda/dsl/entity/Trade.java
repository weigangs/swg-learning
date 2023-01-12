package com.swg1024.learning.java8.lambda.dsl.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年01月11日 22:59
 */
@ToString
@Data
public class Trade {
    public enum Type {BUY, SELL}

    private Type type;

    private Stock stock;
    private int quantity;
    private double price;

    public double getValue() {
        return quantity * price;
    }

}
