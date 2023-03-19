package com.swg1024.learning.java8.concurrent.future;

import com.swg1024.learning.java8.concurrent.entity.Discount;
import com.swg1024.learning.java8.concurrent.entity.Quote;
import com.swg1024.learning.java8.concurrent.entity.Shop;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年03月19日 19:17
 */
public class FutureAndOnCompletionTest {

    List<Shop> shops = List.of(new Shop("BestPrice"), new Shop("LetsSaveBig")
            , new Shop("MyFavoriteShop"), new Shop("BuyItAll"), new Shop("New Shop"));

    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), (Runnable r) -> {
        Thread t = new Thread(r);
        // 使用守护线程，这种方式不会阻止程序的关停
        t.setDaemon(true);
        return t;
    });

    private static final Random random = new Random();

    public static void randomDelay() {
        int delay = 500 + random.nextInt(2000);

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithDiscountRandomDelay(product
        ), executor)).map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }

    /**
     * thenAccept 在CompletableFuture注册一个操作，完成得时候调用
     */
    @Test
    public void test1() {
        CompletableFuture [] futures = findPricesStream("myPhone")
                .map(f -> f.thenAccept(System.out::println)).toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
    }

    /**
     * allOf , 所有执行结果
     * anyOf , 任意一个执行完就结束
     */
    @Test
    public void test2() {
        long start = System.nanoTime();
        CompletableFuture [] futures = findPricesStream("myPhone")
                .map(f -> f.thenAccept(s ->
                        System.out.println(s + "(done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    /**
     * allOf , 所有执行结果
     * anyOf , 任意一个执行完就结束
     */
    @Test
    public void test3() {
        long start = System.nanoTime();
        CompletableFuture [] futures = findPricesStream("myPhone")
                .map(f -> f.thenAccept(s ->
                        System.out.println(s + "(done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.anyOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }
}
