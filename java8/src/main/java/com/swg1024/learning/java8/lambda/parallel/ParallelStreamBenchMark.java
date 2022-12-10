package com.swg1024.learning.java8.lambda.parallel;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 并行流性能测试
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月06日 22:32
 */

/**
 * 测试用于执行基准测试目标方法所花费的平均时间
 */
@BenchmarkMode(Mode.AverageTime)
/**
 * 以毫秒为单位，打印输出基准测试的结果
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
/**
 * 采用4GB的堆，执行基准测试两次以获得更可靠的结果
 */
@Fork(value = 2, jvmArgs={"-Xms4G", "-Xmx4G"})
/**
 * JMH用@State注解来说明对象的生命周期
 */
@State(Scope.Thread)
public class ParallelStreamBenchMark {
    private static final long N = 10_000_000L;

    // 基准测试的目标方法
//    @Benchmark
//    public long sequentialSum() {
//        return Stream.iterate(1L, i -> i + 1).limit(N).reduce(0L, Long::sum);
//    }

//    @Benchmark
//    public long iterativeSum() {
//        long result = 0;
//        for (long i = 1L; i <= N; i++) {
//            result += i;
//        }
//        return result;
//    }

    @Benchmark
    public long parallelRangeSum() {
        return LongStream.rangeClosed(1, N).parallel().reduce(0L, Long::sum);
    }

    // 尽量在每次基准测试迭代结束后都进行一次垃圾回收
    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }

    public static void main(String []args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ParallelStreamBenchMark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
