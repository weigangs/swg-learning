package com.swg1024.learning.java8.lambda.optimization;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 针对环绕业务代码的lambda 优化方案
 *
 * @version 1.0
 * @author: nicholas
 * @createTime: 2022年12月10日 23:16
 */
public class OptimizationTest {

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("D:\\ideaProjects\\product\\swg-learning\\java8\\src\\main\\java\\com\\swg1024\\learning\\java8\\lambda\\optimization\\data.txt"))) {
            return p.process(br);
        }
    }

    @Test
    public void readTwoLines() {
        try {
            System.out.println(processFile(b -> b.readLine() + "\n" + b.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
