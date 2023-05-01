package com.marketplace.vintage.order;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID orderId;
    private final UUID userId;
    private final ArrayList<String> itemsInOrder;
    private BigDecimal totalPrice;
    private OrderStatus orderState;
    private Date returnDate;

    public Order(UUID userId, BigDecimal totalPrice, ArrayList<String> itemsInOrder) {
        this(UUID.randomUUID(), userId, totalPrice, itemsInOrder, OrderStatus.ORDERED);
    }
    public Order(UUID userId) {
        this(UUID.randomUUID(), userId, BigDecimal.valueOf(0), new ArrayList<String>(), OrderStatus.ORDERED);
    }

    public Order(UUID orderId, UUID userId, BigDecimal totalPrice, ArrayList<String> array, OrderStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.itemsInOrder = array;
        this.orderState = status;
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public ArrayList<String> getItemsInOrder() { return this.itemsInOrder; }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public BigDecimal calculateTotalPrice(ItemManager itemManager, int year) {
        int arraySize = this.itemsInOrder.size();
        for (int i = 0; i < arraySize; i++) {
            BigDecimal itemValue = itemManager.getItem(this.itemsInOrder.get(i)).getFinalPrice(year);
            this.totalPrice = this.totalPrice.add(itemValue);
        }

        return this.totalPrice;
    }

    public void setOrdered() {
        this.orderState = OrderStatus.ORDERED;
    }

    public void setIssued() {
        this.orderState = OrderStatus.ISSUED;
    }

    public void setDelivered() {
        this.orderState = OrderStatus.DELIVERED;
    }

}
