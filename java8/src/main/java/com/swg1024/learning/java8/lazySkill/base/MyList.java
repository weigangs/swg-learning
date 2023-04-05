package com.swg1024.learning.java8.lazySkill.base;

import java.util.function.Predicate;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月03日 22:39
 * 演是实时加载及延迟加载例子
 *
 */
public interface MyList<T> {
    T head();
    MyList<T> tail();
    default boolean isEmpty() {
        return true;
    }

    MyList<T> filter(Predicate<T> p);

    /**
     * printAll
     *
      */

    static <T> void printAll(MyList<T> list) {
        if (list.isEmpty()) {
            return;
        }
        System.out.println(list.head());
        // 1. 这里用到一个java目前还不支持的优化特性（tail call elimination） 尾调消除
        // 计算无限质数时，本方法最终会导致栈溢出
        // 尾调消除指直接在传参括号里面计算参数值，且当前层次的函数计算结果不依赖更深层递归调用的结果
        // 反例：f(x) = x + f(x + 1) 该函数的结果依赖与f(x + 1)的结果
        // 2. 另一个概念：尾调优化（tail call optimization），指递归调用中，每层函数调用更深层递归调用时不保留中间值，
        // 且最终递归调用的结果是最末尾的一次调用的计算结果
        printAll(list.tail());
    }
}
