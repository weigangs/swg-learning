package com.swg1024.learning.java8.lambda.collectors;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.function.TriFunction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

/**
 * TODO
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月01日 22:36
 */
public class CollectorsTest {

    Map<String, List<String>> dishTags = new HashMap<>();
    TriFunction<Integer, String, Dish.Type, Dish> supplier =
            (c, n, t) -> ((Supplier<Dish>) Dish::new).get().setCalories(c).setName(n).setType(t);
    List<Dish> menu = Lists.newArrayList(
            supplier.apply(500, "pork", Dish.Type.MEAT),
            supplier.apply(600, "beef", Dish.Type.MEAT),
            supplier.apply(400, "chicken", Dish.Type.MEAT),
            supplier.apply(300, "french fries", Dish.Type.OTHER),
            supplier.apply(450, "rice", Dish.Type.OTHER),
            supplier.apply(200, "season fruit", Dish.Type.OTHER),
            supplier.apply(300, "pizza", Dish.Type.OTHER),
            supplier.apply(300, "prawns", Dish.Type.OTHER),
            supplier.apply(240, "salmon", Dish.Type.FISH));

    @Before
    public void init() {
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));
    }

    @Test
    public void testGroupingBy() {
       Map<Dish.Type, Set<String>> dishByType =
               menu.stream()
                       .collect(groupingBy(Dish::getType,
                               flatMapping(dish-> dishTags.get(dish.getName()).stream(), toSet())));
        System.out.println(dishByType);
    }

    @Test
    public void innerGroupingBy() {
        System.out.println(menu.stream().collect(groupingBy(Dish::getType, groupingBy(dish -> {
            if (dish.getCalories() <= 300) {
                return CaloricLevel.DIET;
            } else if (dish.getCalories() <= 500) {
                return CaloricLevel.NORMAL;
            } else {
                return CaloricLevel.FAT;
            }
        } ))));
    }

    @Test
    public void testGroupingBy2() {
//        Map<Dish.Type, List<String>> map =
//                menu.stream().collect(groupingBy(Dish::getType, mapping(e-> {
//                    return BigDecimal.ZERO;
//                }, reducing(new ArrayList<BigDecimal>(), (List<BigDecimal> list, BigDecimal b) -> {
//                    return list;
//                }, (List<BigDecimal> list1, List<BigDecimal> list2) -> {
//                    return list1;
//                }))));
    }
}

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
class Dish {
    private Type type;
    private Integer calories;
    private String name;

    enum Type{
        MEAT,
        FISH,
        OTHER
    }

}



enum CaloricLevel{

    DIET,
    NORMAL,
    FAT;


}
