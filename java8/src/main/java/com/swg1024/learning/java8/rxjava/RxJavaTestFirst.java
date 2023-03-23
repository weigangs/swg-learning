package com.swg1024.learning.java8.rxjava;

import com.swg1024.learning.java8.reactive.entity.TempInfo;
import com.swg1024.learning.java8.rxjava.observer.TempObserver;
import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月22日 23:04
 */
public class RxJavaTestFirst {

    public static Observable<TempInfo> getTemperature(String town) {
        return Observable.create(emitter -> Observable.interval(1, TimeUnit.SECONDS).subscribe(i -> {
            if (!emitter.isDisposed()) {
                if (i >= 5) {
                    emitter.onComplete();
                } else {
                    try{
                        emitter.onNext(TempInfo.fetch(town));
                    }catch (Exception e) {
                        emitter.onError(e);
                    }
                }
            }
        }));
    }

    @Test
    public void test() {
        Observable<TempInfo> observable = getTemperature("New York");
        observable.blockingSubscribe(new TempObserver());
    }
}
