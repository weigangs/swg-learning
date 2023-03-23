package com.swg1024.learning.java8.rxjava.observer;

import com.swg1024.learning.java8.reactive.entity.TempInfo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月22日 23:03
 */
public class TempObserver implements Observer<TempInfo> {
    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(TempInfo tempInfo) {
        System.out.println(tempInfo);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Got problem: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
}
