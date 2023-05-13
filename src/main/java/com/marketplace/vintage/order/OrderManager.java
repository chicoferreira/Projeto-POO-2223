package com.marketplace.vintage.order;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.utils.AlphanumericGenerator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class OrderManager implements Serializable {

    private static final String ORDER_ID_FORMAT = "ORD-XXXXXX";

    private final Map<String, Order> ordersById;

    public OrderManager() {
        this.ordersById = new HashMap<>();
    }

    public Order getOrder(String id) {
        if (!this.ordersById.containsKey(id)) {
            throw new EntityNotFoundException("An order with the id " + id + " was not found");
        }

        return this.ordersById.get(id).clone();
    }

    public void registerOrder(Order order) {
        String orderId = order.getOrderId();

        if (AlphanumericGenerator.isOfFormat(ORDER_ID_FORMAT, orderId)) {
            throw new IllegalArgumentException("The order id must be of the format " + ORDER_ID_FORMAT);
        }

        if (this.ordersById.containsKey(order.getOrderId())) {
            throw new EntityAlreadyExistsException("An order with that id already exists");
        }

        this.ordersById.put(orderId, order);
    }

    public List<Order> getAll() {
        return this.ordersById.values().stream().map(Order::clone).toList();
    }

    public List<Order> getAll(Predicate<Order> filter) {
        return getAll().stream().filter(filter).toList();
    }

    public List<Order> getAllWithStatus(OrderStatus orderStatus) {
        return getAll(order -> order.getOrderStatus() == orderStatus);
    }

    public boolean containsOrder(String id) {
        return this.ordersById.containsKey(id);
    }

    public String generateUniqueOrderId() {
        String code = AlphanumericGenerator.generateAlphanumericCode(ORDER_ID_FORMAT);
        if (containsOrder(code)) {
            return generateUniqueOrderId();
        }
        return code;
    }

    public void updateOrder(Order order) {
        String orderId = order.getOrderId();

        if (!this.ordersById.containsKey(orderId)) {
            throw new EntityNotFoundException("An order with the id " + orderId + " was not found");
        }

        this.ordersById.put(orderId, order);
    }
}
