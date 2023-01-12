package com.swg1024.learning.java8.lambda.dsl.entity;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年01月11日 23:00
 */
@ToString
public class Order {
    private String customer;
    private List<Trade> trades = new ArrayList<>();

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }

    public double getValue() {
        return trades.stream().mapToDouble(Trade::getValue).sum();
    }
}
