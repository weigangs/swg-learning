package com.swg1024.learning.java8.lambda.stream;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

/**
 * TODO
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年11月27日 17:31
 */
public class StreamApiTest {

    /**
     * common
     */
    BiFunction<Integer, String, Dish> supplier =
            (c, n) -> ((Supplier<Dish>)Dish::new).get().setCalories(c).setName(n);
    Stream<Dish> stream = Lists.newArrayList(supplier.apply(300, "鸡"), supplier.apply(400, "鱼"),
            supplier.apply(700, "猪"), supplier.apply(100, "青菜"), supplier.apply(80, "菠菜"),
            supplier.apply(200, "鸡蛋")).stream();


    @Test
    public void test() {

        stream.filter(e-> {
            System.out.println("filer : " + e.getName());
            return e.getCalories() >= 300;
        }).map(e-> {
            System.out.println("map: " + e.getName());
            return e.getName();
        }).sorted().limit(2).count();

    }

    @Test
    public void testTakeWhile() {
        stream.takeWhile(e-> {
            return e.getCalories() >= 100;
        }).map(e-> {
            return e.getName();
        }).forEach(System.out::println);
    }

    @Test
    public void testDropWhile() {
        stream.dropWhile(e-> {
            return e.getCalories() >= 100;
        }).map(e-> {
            return e.getName();
        }).forEach(System.out::println);
    }

    /**
     * 数对
     */
    @Test
    public void testFlatMap() {
        List<Integer> numbers1 = Lists.newArrayList(1 ,2 ,3);
        List<Integer> numbers2 = Lists.newArrayList(3, 4);
        numbers1.stream().flatMap(n1 -> numbers2.stream().map(n2 -> new int[] {n1, n2})).collect(toList())
                .forEach(e-> System.out.println("{" + e[0] + "," + e[1] + "}"));
        System.out.println("===============>");
        numbers1.stream().flatMap(n1 -> numbers2.stream().filter(n2 -> (n1 + n2)%3 == 0).map(n2 -> new int[] {n1, n2}))
                .collect(toList()).forEach(e-> System.out.println("{" + e[0] + "," + e[1] + "}"));

        List<String> words = Lists.newArrayList("Hello,", "World");

        System.out.println(words.stream().map(e-> e.split("")).flatMap(Arrays::stream).distinct()
                .collect(toList()));

        System.out.println(Stream.of(numbers1, numbers2).map(List::toArray).flatMap(Arrays::stream).collect(toList()));
    }

    @Test
    public void testReduce() {
        List<Integer> numbs = Lists.newArrayList(4, 5, 6, 7);
        Integer sum = numbs.stream().reduce(0, Integer::sum);
        System.out.println(sum);
        Optional<Integer> optional = numbs.stream().reduce((a, b) -> a * b);
        optional.ifPresent(System.out::println);

    }

    public void testFlatMap2() {
        // 勾股数
        Stream<int []> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a-> IntStream.rangeClosed(a, 200)
                        .filter(b -> Math.sqrt(a*a + b*b)%1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)}));
    }

    @Test
    public void testIterate() {
        IntStream.iterate(0, n -> n < 100, n -> n + 4).limit(100).forEach(System.out::println);
    }

    @Test
    public void generate() {
        IntStream nums = IntStream.generate(new IntSupplier() {
            private int num = 2;
            @Override
            public int getAsInt() {
                return num;
            }
        });
    }

    @Test
    public void testReduce3() {
        Stream.of(1, 2, 3, 4, 5).reduce(new ArrayList<Integer>(), (List<Integer> l, Integer e) -> {
            l.add(e);
            return l;
        }, (List<Integer> l1, List<Integer> l2) -> {
            l1.addAll(l2);
            return l1;
        }).forEach(System.out::println);

    }
}

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
class Dish {

    private Integer calories;
    private String name;

}
