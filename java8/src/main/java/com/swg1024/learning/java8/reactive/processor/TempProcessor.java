package com.swg1024.learning.java8.reactive.processor;

import com.swg1024.learning.java8.reactive.entity.TempInfo;

import java.util.concurrent.Flow.*;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月20日 22:05
 */
public class TempProcessor implements Processor<TempInfo, TempInfo> {

    private Subscriber<? super TempInfo> subscriber;

    @Override
    public void subscribe(Subscriber<? super TempInfo> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(TempInfo item) {
        subscriber.onNext(new TempInfo(item.getTown(),
                (item.getTemp() - 32) * 5 / 9));
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
