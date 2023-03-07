package com.swg1024.learning.java8.concurrent.future;

import com.swg1024.learning.java8.concurrent.entity.Shop;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月02日 23:26
 *
 * // 根据硬件判断，当机器有四个线程时，任务个数为4个，2比3快，当任务增加到5个时，2和3差不多，甚至3比2还快
 */
public class StreamAndFutureTest {
    List<Shop> shops = List.of(new Shop("BestPrice"), new Shop("LetsSaveBig")
    , new Shop("MyFavoriteShop"), new Shop("BuyItAll"), new Shop("New Shop"));

    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), (Runnable r) -> {
        Thread t = new Thread(r);
        // 使用守护线程，这种方式不会阻止程序的关停
        t.setDaemon(true);
        return t;
    });

    public List<String> findPrices1(String product) {
        return shops.stream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }



    public List<String> findPrices2(String product) {
        return shops.parallelStream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }



    public List<String> findPrices3(String product) {
        List<CompletableFuture<String>> priceFutrues = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() ->
                        String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))).collect(Collectors.toList());
        return priceFutrues.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<String> findPrices4(String product) {
        List<CompletableFuture<String>> priceFutrues = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() ->
                        String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor)).collect(Collectors.toList());
        return priceFutrues.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    @Test
    public void test1() {
        long start = System.nanoTime();
        System.out.println(findPrices1("myPhone27"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    @Test
    public void test2() {
        long start = System.nanoTime();
        System.out.println(findPrices2("myPhone27"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    @Test
    public void test3() {
        long start = System.nanoTime();
        System.out.println(findPrices3("myPhone27"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    @Test
    public void test4() {
        long start = System.nanoTime();
        System.out.println(findPrices4("myPhone27"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }
}
