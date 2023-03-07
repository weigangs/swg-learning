package com.swg1024.learning.java8.concurrent.entity;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月02日 23:27
 */
public class Shop {

    private String name;
    private Random random = new Random();


    public Shop(String name) {
        this.name = name;
    }

    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(()-> caculatePrice(product));
    }

    public double getPrice(String product) {
        return this.caculatePrice(product);
    }

    public String getPriceWithDiscount(String product) {
        double price =  this.caculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }


    public double caculatePrice(String product)  {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public void delay() {
        try{
            Thread.sleep(1000L);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return this.name;
    }


}
