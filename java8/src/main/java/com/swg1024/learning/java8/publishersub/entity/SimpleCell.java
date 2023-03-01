package com.swg1024.learning.java8.publishersub.entity;


import com.swg1024.learning.java8.publishersub.base.Publisher;
import com.swg1024.learning.java8.publishersub.base.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年02月25日 9:58
 */
public class SimpleCell implements Publisher<Integer>, Subscriber<Integer> {

    private int value = 0;
    private String name;
    private List<Subscriber> subscribers = new ArrayList<>();

    public SimpleCell(String name) {
        this.name = name;
    }

    @Override
    public void onNext(Integer item) {
        this.value = item;
        System.out.println(this.name + ":" + this.value);
        notifyAllSubscribers();
    }


    private void notifyAllSubscribers() {
        subscribers.forEach(subscriber -> subscriber.onNext(this.value));
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        subscribers.add(subscriber);
    }
}
