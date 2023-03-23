package com.swg1024.learning.java8.reactive;

import com.swg1024.learning.java8.reactive.entity.TempInfo;
import com.swg1024.learning.java8.reactive.processor.TempProcessor;
import com.swg1024.learning.java8.reactive.subscriber.TempSubscriber;
import com.swg1024.learning.java8.reactive.subscription.TempSubscription;
import org.junit.Test;

import java.util.concurrent.Flow.*;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月20日 21:41
 */
public class FirstPubSubTest {

    @Test
    public void test() {
        getTemperatures("New York").subscribe(new TempSubscriber());
    }

    @Test
    public void test2() {
        getCelsiusTemperatures("New York").subscribe(new TempSubscriber());
    }

    public Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

    public Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }
}
