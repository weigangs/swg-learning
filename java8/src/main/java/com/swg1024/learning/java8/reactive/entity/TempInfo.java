package com.swg1024.learning.java8.reactive.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.Random;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月20日 21:30
 */

@ToString
@Getter
public class TempInfo {
    public static final Random random = new Random();

    private final String town;
    private final int temp;

    public TempInfo(String town, int temp) {
        this.town = town;
        this.temp = temp;
    }

    public static TempInfo fetch(String town) {
        if (random.nextInt(10) == 0) {
            throw new RuntimeException("Error!");
        }
        return new TempInfo(town, random.nextInt(100));
    }
}
