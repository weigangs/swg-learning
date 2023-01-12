package com.swg1024.learning.java8.lambda.collectors;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;


/**
 * TODO
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年01月10日 23:23
 */
public class CollectingAndThenTest {

    /**
     * common
     */
    BiFunction<Integer, String, Dish> supplier =
            (c, n) -> ((Supplier<Dish>) Dish::new).get().setCalories(c).setName(n);
    Stream<Dish> stream = Lists.newArrayList(supplier.apply(300, "鸡"), supplier.apply(400, "鱼"),
            supplier.apply(700, "猪"), supplier.apply(100, "青菜"), supplier.apply(80, "菠菜"),
            supplier.apply(200, "鸡蛋")).stream();

    public void testCollAndThen1() {
       List<Dish> dishes = stream.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

}


