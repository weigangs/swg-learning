package com.swg1024.learning.java8.lambda.patterndesign;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 向已注册的观察者推送信息
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月10日 23:53
 */
public class Feed implements Subject{
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void notifyObservers(String tweet) {
        observers.forEach(o -> o.notify(tweet));
    }

    @Test
    public void test() {
        Feed f = new Feed();
        f.registerObserver(tweet -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });
    }
}
