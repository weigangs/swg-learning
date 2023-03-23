package com.swg1024.learning.java8.reactive.subscriber;


import com.swg1024.learning.java8.reactive.entity.TempInfo;

import java.util.concurrent.Flow.*;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月20日 21:38
 */
public class TempSubscriber implements Subscriber<TempInfo> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(TempInfo item) {
        System.out.println(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
}
