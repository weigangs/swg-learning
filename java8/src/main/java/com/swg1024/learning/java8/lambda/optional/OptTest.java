package com.swg1024.learning.java8.lambda.optional;

import com.swg1024.learning.java8.lambda.optional.util.OptionalUtil;
import org.junit.Test;

import java.util.Optional;
import java.util.Properties;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年01月31日 21:43
 */
public class OptTest {

    @Test
    public void test1() {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");
    }

    public int readDurationOld(Properties props, String name) {
        String value = props.getProperty(name);

        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException e) {}
        }
        return 0;
    }

    public int readDurationNew(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name)).flatMap(OptionalUtil::stringToInt).filter(i -> i > 0).orElse(0);
    }

}
