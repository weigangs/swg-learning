package com.swg1024.learning.java8.concurrent.entity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static java.lang.String.format;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月07日 22:42
 */
public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;
        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apply(double price, Code code) {
        delay();
        double num = price * (100 - code.percentage) / 100;
        return (double)Math.round(num*100)/100;
    }

    public static void delay() {
        try{
            Thread.sleep(1000L);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
