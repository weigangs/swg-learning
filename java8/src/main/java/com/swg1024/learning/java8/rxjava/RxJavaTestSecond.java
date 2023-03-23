package com.swg1024.learning.java8.rxjava;

import com.swg1024.learning.java8.reactive.entity.TempInfo;
import com.swg1024.learning.java8.rxjava.observer.TempObserver;
import io.reactivex.Observable;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月23日 21:03
 */
public class RxJavaTestSecond {

    public static Observable<TempInfo> getCelsiusTemperatures(String town) {
        return RxJavaTestFirst.getTemperature(town).map(item -> new TempInfo(item.getTown(),
                (item.getTemp() - 32) * 5 / 9));
    }

    /**
     * merge 合并多个Observable
     * map 对发布的消息进行转换
     * filter 对发布的消息进行过滤
     * @param town city
     * @return observable
     */

    public static Observable<TempInfo> getTemperature(String ... town) {
        return Observable.merge(Arrays.stream(town).map(subTown -> getCelsiusTemperatures(subTown)
                .filter(third -> third.getTemp() <= 0)).collect(Collectors.toList()));
    }

    @Test
    public void test() {
        getTemperature("New York", "Beijing", "Xian").blockingSubscribe(new TempObserver());
    }
}
