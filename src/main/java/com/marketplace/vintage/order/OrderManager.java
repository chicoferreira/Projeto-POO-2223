package com.marketplace.vintage.order;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

public class OrderManager {

    private final Map<UUID, Order> ordersById;

    public OrderManager() { this.ordersById = new HashMap<>(); }

    public Order getOrder(UUID id) {
        if (!this.ordersById.containsKey(id)) {
            throw new EntityNotFoundException("An order with the id " + id + " was not found");
        }

        return this.ordersById.get(id);
    }

    public void registerOrder(Order order) {
        UUID orderId = order.getOrderId();

        if (this.ordersById.containsKey(order)) {
            throw new EntityAlreadyExistsException("An order with that id already exists");
        }

        this.ordersById.put(orderId, order);
    }
}
