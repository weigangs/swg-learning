package com.swg1024.learning.java8.concurrent.future;

import com.swg1024.learning.java8.concurrent.entity.Discount;
import com.swg1024.learning.java8.concurrent.entity.Quote;
import com.swg1024.learning.java8.concurrent.entity.Shop;
import com.swg1024.learning.java8.concurrent.service.ExchangeService;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月07日 23:03
 */
public class FutureWeaverTest {
    List<Shop> shops = List.of(new Shop("BestPrice"), new Shop("LetsSaveBig")
            , new Shop("MyFavoriteShop"), new Shop("BuyItAll"), new Shop("New Shop"));

    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), (Runnable r) -> {
        Thread t = new Thread(r);
        // 使用守护线程，这种方式不会阻止程序的关停
        t.setDaemon(true);
        return t;
    });

    private ExchangeService exchangeService;

    public List<String> findPrices1(String product) {
        return shops.stream().map(shop -> shop.getPriceWithDiscount(product))
                .map(Quote::parse).map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPrices2(String product) {
        List<CompletableFuture<String>> priceFutrues = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithDiscount(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(Collectors.toList());
        return priceFutrues.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<Future<Double>> testCombine(String product) {
        return shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product))
                .thenCombine(CompletableFuture.supplyAsync(() ->
                        exchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                        (price, rate) -> price * rate)).collect(Collectors.toList());
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


}
