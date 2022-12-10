package com.swg1024.learning.java8.lambda.parallel.forJoinPool;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 自然数累加，并行累加
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月07日 22:25
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    // 要求和的数组
    private final long [] numbers;

    // 由子任务处理的子数组的起始和终止位置
    private final int start;
    private final int end;

    // 将任务分解为子任务的闸值
    public static final long THRESHHOLD = 10_000;

    // 公共构造函数用于创建主任务
    public ForkJoinSumCalculator(long [] numbers) {
        this(numbers, 0, numbers.length);
    }

    // 私有构造函数用于以递归方式为主任务创建子任务
    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;

    }


    // 重写recursiveTask抽象方法
    @Override
    protected Long compute() {
        // 该任务负责求和的子数组大小
        int length = end - start;

        if (length <= THRESHHOLD) {
            // 如果大小小于或等于闸值，就顺序计算结果
            return computeSequentially();
        }
        // 创建一个子任务为数组的前一半求和
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
        // 利用ForkJoinPool的另一个线程异步地执行新创建地子任务
        leftTask.fork();
        // 创建一个任务为数组地后一半求和
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
        // 同步执行第二个子任务
        Long rightResult = rightTask.compute();
        // 读取第一个子任务地结果，如果尚未完成就等待
        Long leftResult = leftTask.join();
        // 整个两个子任务地结果
        return leftResult + rightResult;

    }

    // 大小小于闸值时所采用的一个简单的顺序算法
    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void test() {
        long [] numbers = LongStream.rangeClosed(1, 1_000_000).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        System.out.println(new ForkJoinPool().invoke(task));
    }

    public static void main(String []args) {
        test();
    }


}
