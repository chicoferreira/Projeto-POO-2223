package com.marketplace.vintage.order;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class OrderManager {

    private final Map<String, Order> ordersById;

    public OrderManager() {
        this.ordersById = new HashMap<>();
    }

    public Order getOrder(String id) {
        if (!this.ordersById.containsKey(id)) {
            throw new EntityNotFoundException("An order with the id " + id + " was not found");
        }

        return this.ordersById.get(id);
    }

    public void registerOrder(Order order) {
        String orderId = order.getOrderId();

        if (this.ordersById.containsKey(order.getOrderId())) {
            throw new EntityAlreadyExistsException("An order with that id already exists");
        }

        this.ordersById.put(orderId, order);
    }
}
