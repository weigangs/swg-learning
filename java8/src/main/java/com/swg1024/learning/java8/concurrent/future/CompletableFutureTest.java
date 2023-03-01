package com.swg1024.learning.java8.concurrent.future;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年02月23日 21:59
 */
public class CompletableFutureTest {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        executorService.submit(() -> a.complete(f(x)));
        System.out.println(a.get() + g(x));
        executorService.shutdown();
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        //50019707
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;
        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();
        CompletableFuture<Integer> c = a.thenCombine(b, (y, z) -> y + z);
        executorService.submit(() -> a.complete(f(x)));
        executorService.submit(() -> b.complete(g(x)));
        System.out.println(c.get());
        executorService.shutdown();

    }

    public int f(int x) {
        return x + IntStream.rangeClosed(1, 100_00).reduce(0, ((left, right) -> left + right));
    }

    public int g(int x) {
        return x * 10;
    }


    @Test
    public Future<Double> getPriceAsync1(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = caculatePrice(product);
                futurePrice.complete(price);
            }catch (Exception e) {
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    @Test
    public Future<Double> getPriceAsync2(String product) {
        return CompletableFuture.supplyAsync(()-> caculatePrice(product));
    }


    public double caculatePrice(String product)  {
        delay();
        return Math.random() + product.charAt(0);
    }

    public void delay() {
        try{
            Thread.sleep(1000L);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
