package com.swg1024.learning.java8.lambda.optional.util;

import java.util.Optional;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年01月31日 21:40
 */
public class OptionalUtil {

    /**
     * flatMap, extract inner element
     *         Integer a = 3;
     *         Optional<Integer> opt = Optional.of(a);
     *         String b = opt.flatMap(e -> Optional.of(e.toString())).orElse("");
     * @param s
     * @return
     */
    public static Optional<Integer> stringToInt(String s) {
        try{
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
