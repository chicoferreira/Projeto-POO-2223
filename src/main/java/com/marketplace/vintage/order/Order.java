package com.marketplace.vintage.order;

import com.marketplace.vintage.utils.VintageDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Order {

    private final String orderId;
    private final UUID userId;
    private final List<ItemOrder> orderedItems;
    private final Map<String, BigDecimal> parcelCarrierPrices;
    private final BigDecimal totalPrice;
    private final VintageDate orderDate;
    private OrderStatus orderStatus;

    public Order(String orderId, UUID userId, List<ItemOrder> orderedItems, Map<String, BigDecimal> parcelCarrierPrices, BigDecimal totalPrice, VintageDate orderDate) {
        this(orderId, userId, orderedItems, parcelCarrierPrices, totalPrice, orderDate, OrderStatus.ORDERED);
    }

    public Order(String orderId, UUID userId, List<ItemOrder> orderedItems, Map<String, BigDecimal> parcelCarrierPrices, BigDecimal totalPrice, VintageDate orderDate, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderedItems = orderedItems;
        this.parcelCarrierPrices = parcelCarrierPrices;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<ItemOrder> getOrderedItems() {
        return new ArrayList<>(orderedItems); // ItemOrder is immutable
    }

    public List<ItemOrder> getOrderedItemsByParcelCarrier(String parcelCarrierName) {
        return orderedItems.stream().filter(itemOrder -> itemOrder.getParcelCarrierName().equals(parcelCarrierName)).toList();
    }

    public BigDecimal getParcelCarrierShippingCost(String parcelCarrierName) {
        BigDecimal bigDecimal = parcelCarrierPrices.get(parcelCarrierName);
        if (bigDecimal == null) throw new IllegalArgumentException("Parcel carrier name not found");

        return bigDecimal;
    }

    public List<String> getAllParcelCarrierNames() {
        return new ArrayList<>(parcelCarrierPrices.keySet());
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public VintageDate getOrderDate() {
        return orderDate;
    }
}
