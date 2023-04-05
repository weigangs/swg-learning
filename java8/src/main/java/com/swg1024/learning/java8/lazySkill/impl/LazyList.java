package com.swg1024.learning.java8.lazySkill.impl;

import com.swg1024.learning.java8.lazySkill.base.MyList;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月03日 22:46
 * 延迟列表
 */
@AllArgsConstructor
public class LazyList<T> implements MyList<T> {

    private final T head;
    // 不调用tail（）方法时，tail未创建
    private final Supplier<MyList<T>> tail;

    @Override
    public T head() {
        return this.head;
    }

    @Override
    public MyList<T> tail() {
        return this.tail.get();
    }

    @Override
    public MyList<T> filter(Predicate<T> p) {
        return isEmpty() ? this :
                p.test(head()) ? new LazyList<>(head(), () -> tail().filter(p)) :
                        tail().filter(p);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
