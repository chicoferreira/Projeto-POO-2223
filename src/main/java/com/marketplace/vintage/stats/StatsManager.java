package com.marketplace.vintage.stats;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.VintageDate;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class StatsManager {

    private final Vintage vintage;

    public StatsManager(Vintage vintage) {
        this.vintage = vintage;
    }

    private <T> Comparator<T> getBigDecimalComparator(Function<T, BigDecimal> valueGetter) {
        return (o1, o2) -> {
            BigDecimal o1Value = valueGetter.apply(o1);
            BigDecimal o2Value = valueGetter.apply(o2);
            return o1Value.compareTo(o2Value);
        };
    }

    private <T> @Nullable T getMax(Collection<T> collection, Function<T, BigDecimal> valueGetter) {
        if (collection.isEmpty()) return null;
        return Collections.max(collection, getBigDecimalComparator(valueGetter));
    }

    private <T> List<T> getTop(Collection<T> collection, Function<T, BigDecimal> valueGetter, int limit) {
        return collection.stream().sorted(getBigDecimalComparator(valueGetter).reversed())
                .limit(limit)
                .toList();
    }

    public @Nullable User getSellerWithMoreMoneySales(Predicate<VintageDate> datePredicate) {
        return getMax(vintage.getAllUsers(), user -> getMoneyFromUserSalesByDatePredicate(user, datePredicate));
    }

    public @Nullable ParcelCarrier getParcelCarrierWithMoreMoneyReceived() {
        return getMax(vintage.getAllParcelCarriers(), this::getParcelCarrierReceivedMoney);
    }

    public List<User> getTopBuyers(int limit, Predicate<VintageDate> datePredicate) {
        return getTop(vintage.getAllUsers(), user -> getMoneySpentByUserInDatePredicate(user, datePredicate), limit);
    }

    public List<User> getTopSellers(int limit, Predicate<VintageDate> datePredicate) {
        return getTop(vintage.getAllUsers(), user -> getMoneyFromUserSalesByDatePredicate(user, datePredicate), limit);
    }

    public BigDecimal getVintageProfit() {
        BigDecimal total = BigDecimal.ZERO;
        for (Order order : vintage.getAllOrders()) {
            if (!order.hasBeenReturned()) {
                total = total.add(order.getSumOfSatisfactionPrices());
            }
        }
        return total;
    }

    // We opted to not save the total money received by an entity (User, ParcelCarrier, etc) in its model class
    // because saving all related orders makes it easier to implement more stats in the future.
    public BigDecimal getParcelCarrierReceivedMoney(ParcelCarrier parcelCarrier) {
        BigDecimal total = BigDecimal.ZERO;
        for (String deliveredOrder : parcelCarrier.getDeliveredOrders()) {
            Order order = vintage.getOrder(deliveredOrder);
            if (!order.hasBeenReturned()) {
                total = total.add(order.getParcelCarrierShippingCost(parcelCarrier.getName()));
            }
        }
        return total;
    }

    public BigDecimal getMoneySpentByUserInDatePredicate(User user, Predicate<VintageDate> datePredicate) {
        BigDecimal total = BigDecimal.ZERO;
        for (String orderId : user.getCompletedOrderIdsList()) {
            Order order = vintage.getOrder(orderId);
            if (!order.hasBeenReturned() && datePredicate.test(order.getOrderDate())) {
                total = total.add(order.getTotalPrice());
            }
        }
        return total;
    }

    public BigDecimal getMoneyFromUserSalesByDatePredicate(User user, Predicate<VintageDate> datePredicate) {
        BigDecimal total = BigDecimal.ZERO;
        for (String orderId : user.getCompletedSellsOrderIdsList()) {
            Order order = vintage.getOrder(orderId);
            if (!order.hasBeenReturned() && datePredicate.test(order.getOrderDate())) {
                total = total.add(order.getSumOfItemPricesFromSeller(user.getId()));
            }
        }
        return total;
    }

}
