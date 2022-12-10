package com.swg1024.learning.java8.lambda.patterndesign;

/**
 */
public interface Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}
