package com.swg1024.learning.java8.publishersub.base;

public interface Subscriber<T> {

    void onNext(T t);
}
