package com.swg1024.learning.java8.lambda.collectors;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月05日 21:49
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    @Override
    public Supplier<List<T>> supplier() {
        return null;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return null;
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }

    @Test
    public void testC() {
        System.out.println(Math.sqrt(2));
    }

}
