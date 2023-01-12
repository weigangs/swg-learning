package com.swg1024.learning.java8.lambda.dsl.builder;

import com.swg1024.learning.java8.lambda.dsl.entity.Order;
import com.swg1024.learning.java8.lambda.dsl.entity.Stock;
import com.swg1024.learning.java8.lambda.dsl.entity.Trade;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年01月11日 23:03
 */
public class MethodChainingOrderBuilder {
    private  final Order order = new Order();

    private MethodChainingOrderBuilder(String customer) {
        this.order.setCustomer(customer);
    }

    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer);
    }

    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    }

    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    }

    public MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrade(trade);
        return this;
    }

    public Order end() {
        return order;
    }


    public class TradeBuilder {
        private final MethodChainingOrderBuilder builder;
        public final Trade trade = new Trade();

        private TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
            this.builder = builder;
            this.trade.setType(type);
            this.trade.setQuantity(quantity);
        }

        public StockBuilder stock(String symbol) {
            return new StockBuilder(builder, trade, symbol);
        }

    }

    public class StockBuilder {
        private final MethodChainingOrderBuilder builder;
        private final Trade trade;
        private final Stock stock = new Stock();

        private StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
            this.builder = builder;
            this.trade = trade;
            this.stock.setSymbol(symbol);
        }

        public TradeBuilderWithStock on(String market) {
            stock.setMarket(market);
            trade.setStock(stock);
            return new TradeBuilderWithStock(builder, trade);
        }
    }

    public class TradeBuilderWithStock {
        private final MethodChainingOrderBuilder builder;
        private final Trade trade;

        public TradeBuilderWithStock(MethodChainingOrderBuilder builder, Trade trade) {
            this.builder = builder;
            this.trade = trade;
        }

        public MethodChainingOrderBuilder at(double price) {
            trade.setPrice(price);
            return builder.addTrade(trade);
        }
    }
}
