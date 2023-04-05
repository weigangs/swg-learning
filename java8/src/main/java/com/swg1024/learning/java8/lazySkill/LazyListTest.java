package com.swg1024.learning.java8.lazySkill;

import com.swg1024.learning.java8.lazySkill.base.MyList;
import com.swg1024.learning.java8.lazySkill.impl.LazyList;
import com.swg1024.learning.java8.lazySkill.impl.MyLinkedList;
import org.junit.Test;

import java.util.function.Predicate;
import java.util.stream.LongStream;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月03日 22:44
 */
public class LazyListTest {

    public void test1() {
        MyList<Integer> primes = new MyLinkedList<>(10, new Empty<>());
    }

    @Test
    public void test2() {
        LazyList<Integer> numbers = from(2);
        int two = numbers.head();
        int three = numbers.tail().head();
        int four = numbers.tail().tail().head();
        System.out.println(two + " " + three + "  " + four);
    }

    @Test
    public void test3() {
        LazyList<Integer> numbers = from(2);
        int two = primes(numbers).head();
        int three = primes(numbers).tail().head();
        MyList<Integer> f1 = primes(numbers);
        MyList<Integer> f2 = f1.tail();
        MyList<Integer> f3 = f2.tail();
        int five = primes(numbers).tail().tail().head();
        System.out.println(two + " " + three + "  " + five);
    }

    public static MyList<Integer> primes(MyList<Integer> numbers) {
        MyList<Integer> result =  new LazyList<>(
                numbers.head(),
                ()-> primes(
                        numbers.tail()
                                .filter(n -> n % numbers.head() != 0)
                )
        );
        return result;
    }



    public static LazyList<Integer> from(int n) {
        return new LazyList<>(n, () -> from(n + 1));
    }

    /**
     * 演示尾调优化（tail-call optimization） 以阶乘为例
     * 1. 普通的求阶乘方法
     */

    static long factorialStreams(long n) {
        return LongStream.rangeClosed(1, n).reduce(1, (a, b) -> a *b);
    }

    /**
     * 2. 普通递归方法
     * @param n n
     * @return 阶乘
     */
    static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialStreams(n - 1);
    }

    /**
     *3. 尾-递 方法
     * @param n n
     * @return 阶乘
     */
    static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }

    /**
     * 该方法是 尾-递 类型函数，原因是递归调用发生在方法的最后
     * @param acc
     * @param n
     * @return
     */
    static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n -1);
    }

}


class Empty<T> implements MyList<T> {

    @Override
    public T head() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MyList<T> tail() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MyList<T> filter(Predicate<T> p) {
        return null;
    }
}
