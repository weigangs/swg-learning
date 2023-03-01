package com.swg1024.learning.java8.publishersub.base;

/**
 * PECS
 * : Producer extends (AKA publisher)
 * : Consumer super (AKA subscriber)
 * :
 * @param <T>
 */
public interface Publisher<T> {

    void subscribe(Subscriber<? super T> subscriber);
}
