package com.swg1024.learning.java8.reactive.subscription;

import com.swg1024.learning.java8.reactive.entity.TempInfo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.*;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月20日 21:31
 */
public class TempSubscription implements Subscription {

    // 添加线程池解决subscription.request 和 subscriber.onNext 两个方法递归调用，最终导致栈溢出的问题
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final Subscriber<? super TempInfo> subscriber;
    private final String town;

    public TempSubscription(Subscriber<? super TempInfo> subscriber, String town) {
        this.subscriber = subscriber;
        this.town = town;
    }

    @Override
    public void request(long n) {
        for (long i = 0L; i < n; i++) {
            try{
                subscriber.onNext(TempInfo.fetch(town));
            }catch (Exception e) {
                subscriber.onError(e);
                break;
            }
        }
        // 用线程池改进
//        executor.submit(() -> {
//            for (long i = 0L; i < n; i++) {
//
//                try{
//                    subscriber.onNext(TempInfo.fetch(town));
//                }catch (Exception e) {
//                    subscriber.onError(e);
//                    break;
//                }
//            }
//        });

    }

    @Override
    public void cancel() {
        subscriber.onComplete();
    }
}
