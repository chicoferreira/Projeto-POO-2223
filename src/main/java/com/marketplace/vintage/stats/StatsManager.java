package com.marketplace.vintage.stats;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.utils.VintageDate;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class StatsManager {

    private final UserManager userManager;
    private final OrderManager orderManager;
    private final ParcelCarrierManager parcelCarrierManager;

    public StatsManager(UserManager userManager, OrderManager orderManager, ParcelCarrierManager parcelCarrierManager) {
        this.userManager = userManager;
        this.orderManager = orderManager;
        this.parcelCarrierManager = parcelCarrierManager;
    }

    private <T> Comparator<T> getBigDecimalComparator(Function<T, BigDecimal> valueGetter) {
        return (o1, o2) -> {
            BigDecimal o1Value = valueGetter.apply(o1);
            BigDecimal o2Value = valueGetter.apply(o2);
            return o1Value.compareTo(o2Value);
        };
    }

    private <T> T getMax(Collection<T> collection, Function<T, BigDecimal> valueGetter) {
        return Collections.max(collection, getBigDecimalComparator(valueGetter));
    }

    private <T> List<T> getTop(Collection<T> collection, Function<T, BigDecimal> valueGetter, int limit) {
        return collection.stream().sorted(getBigDecimalComparator(valueGetter))
                .limit(limit)
                .toList();
    }

    public User getSellerWithMoreMoneySales(Predicate<VintageDate> datePredicate) {
        return getMax(userManager.getAll(), user -> getMoneyFromSalesByDatePredicate(user, datePredicate));
    }

    public ParcelCarrier getParcelCarrierWithMoreMoneyReceived() {
        return getMax(parcelCarrierManager.getAll(), this::getParcelCarrierReceivedMoney);
    }

    public List<User> getTopBuyers(int limit, Predicate<VintageDate> datePredicate) {
        return getTop(userManager.getAll(), user -> getMoneySpentInDatePredicate(user, datePredicate), limit);
    }

    public List<User> getTopSellers(int limit, Predicate<VintageDate> datePredicate) {
        return getTop(userManager.getAll(), user -> getMoneyFromSalesByDatePredicate(user, datePredicate), limit);
    }

    public BigDecimal getVintageProfit() {
        BigDecimal total = BigDecimal.ZERO;
        for (Order order : orderManager.getAll()) {
            total = total.add(order.getSumOfSatisfactionPrices());
        }
        return total;
    }

    // We opted to not save the total money received by an entity (User, ParcelCarrier, etc) in its model class
    // because saving all related orders makes it easier to implement more stats in the future.
    public BigDecimal getParcelCarrierReceivedMoney(ParcelCarrier parcelCarrier) {
        BigDecimal total = BigDecimal.ZERO;
        for (String deliveredOrder : parcelCarrier.getDeliveredOrders()) {
            Order order = orderManager.getOrder(deliveredOrder);
            total = order.getParcelCarrierShippingCost(parcelCarrier.getName());
        }
        return total;
    }

    public BigDecimal getMoneySpentInDatePredicate(User user, Predicate<VintageDate> datePredicate) {
        BigDecimal total = BigDecimal.ZERO;
        for (String orderId : user.getCompletedOrderIdsList()) {
            Order order = orderManager.getOrder(orderId);
            if (datePredicate.test(order.getOrderDate())) {
                total = total.add(order.getTotalPrice());
            }
        }
        return total;
    }

    public BigDecimal getMoneyFromSalesByDatePredicate(User user, Predicate<VintageDate> datePredicate) {
        BigDecimal total = BigDecimal.ZERO;
        for (String orderId : user.getCompletedSellsOrderIdsList()) {
            Order order = orderManager.getOrder(orderId);
            if (datePredicate.test(order.getOrderDate())) {
                total = total.add(order.getSumOfItemPricesFromSeller(user.getId()));
            }
        }
        return total;
    }

}
