package com.swg1024.learning.java8.concurrent.entity;

import lombok.Data;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月07日 22:48
 */
@Data
public class Quote {
    private final String  shopName;
    private final double price;
    private final Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code code) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = code;
    }

    public static Quote parse(String s) {
        String [] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }

}
