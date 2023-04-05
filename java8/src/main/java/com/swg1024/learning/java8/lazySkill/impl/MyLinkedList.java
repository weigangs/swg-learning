package com.swg1024.learning.java8.lazySkill.impl;

import com.swg1024.learning.java8.lazySkill.base.MyList;
import lombok.AllArgsConstructor;

import java.util.function.Predicate;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月03日 22:40
 * 实时加载列表，每个元素都在内存中真是存在
 */
@AllArgsConstructor
public class MyLinkedList<T> implements MyList<T> {

    private final T head;
    // tail 是真是存在于内存中的对象，不考虑null
    private final MyList<T> tail;



    @Override
    public T head() {
        return this.head;
    }

    @Override
    public MyList<T> tail() {
        return this.tail;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public MyList<T> filter(Predicate<T> p) {
        return isEmpty() ? this :
                p.test(head()) ? new LazyList<T>(head(), () -> tail().filter(p)) :
                        tail().filter(p);
    }
}

