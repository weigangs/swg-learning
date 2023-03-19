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
        delay();
        return 1.2;
    }

    public void delay() {
        try{
            Thread.sleep(1000L);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
