package com.swg1024.learning.java8.lambda.patterndesign;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 工厂
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月11日 17:14
 */
public class ProductFactory {
    public static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }
    public static Product createProduct(String name) {
        Supplier<Product> p = map.get(name);
        if (p != null) {
            return p.get();
        }
        throw new IllegalArgumentException("No such product " + name);
    }
}


interface Product{

}

class Loan implements Product{

}

class Stock implements Product{

}

class Bond implements Product{

}
