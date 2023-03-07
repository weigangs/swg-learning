package com.swg1024.learning.java8.concurrent.service;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月07日 23:25
 */
public class ExchangeService {
    public enum Money{
        EUR, USD;
    }

    public double getRate(Money from, Money to) {
        return 1.2;
    }
}
