package com.swg1024.learning.java8.lambda.collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 18:33
 */
public class GroupingByTest {

    @Test
    public void test() {
        BiFunction<AEntity, BEntity, ABEntity>  func = (a, b) -> new ABEntity(a, b);
        Stream<ABEntity> stream =  Stream.of(func.apply(new AEntity(1, 0), new BEntity(20, 175)),
                func.apply(new AEntity(2, 3), new BEntity(35, 178)),
                func.apply(new AEntity(2, 5), new BEntity(28, 180)));
        Collector<ABEntity, List<BEntity>, List<BEntity>> collector =
                Collector.of(ArrayList::new,
                (l1, t) -> l1.add(t.getB()),
                (list1,  list2) ->{
                    list1.addAll(list2);
                    return list1;
                    },
                Collector.Characteristics.IDENTITY_FINISH);
        Map<Integer, List<BEntity>> result = stream.collect(Collectors.groupingBy(key -> key.getA().getLevel(), collector));

        System.out.println(result);
    }
    @AllArgsConstructor
    @Data
    static class ABEntity {
        private AEntity a;
        private BEntity b;
    }

    @AllArgsConstructor
    @Data
    static class AEntity {
        private int level;
        private int status;
    }

    @AllArgsConstructor
    @Data
    static class BEntity {

        private int age;

        private int height;

    }
}
