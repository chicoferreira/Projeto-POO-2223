package com.marketplace.vintage.order;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.VintageDate;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class OrderController {

    private final OrderManager orderManager;
    private final OrderFactory orderFactory;

    public OrderController(OrderManager orderManager, OrderFactory orderFactory) {
        this.orderManager = orderManager;
        this.orderFactory = orderFactory;
    }

    public void notifyTimeChange(Logger logger, VintageDate newDate) {
        int ordersChanged = 0;
        List<Order> status = orderManager.getAllWithStatus(OrderStatus.ORDERED);
        for (Order order : status) {
            VintageDate deliverDate = order.getOrderDate().plusDays(VintageConstants.DAYS_TO_DELIVER_ORDER);
            if (deliverDate.isBeforeOrSame(newDate)) {
                setOrderDelivered(order, deliverDate);
                ordersChanged++;
            }
        }

        if (ordersChanged > 0) {
            logger.info("Delivered " + ordersChanged + " orders.");
        }
    }

    public void setOrderDelivered(Order order, VintageDate currentDate) {
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliverDate(currentDate);
        this.orderManager.updateOrder(order);
    }

    public boolean isOrderReturnable(Order order, VintageDate currentDate) {
        return order.getOrderStatus() == OrderStatus.DELIVERED &&
                order.getDeliverDate().distance(currentDate) <= VintageConstants.DAYS_TO_RETURN_ORDER;
    }

    public void returnOrder(Order order) {
        order.setOrderStatus(OrderStatus.RETURNED);
        this.orderManager.updateOrder(order);
    }

    public Order getOrder(String id) {
        return this.orderManager.getOrder(id);
    }

    public void registerOrder(Order order) {
        this.orderManager.registerOrder(order);
    }

    public List<Order> getAll() {
        return this.orderManager.getAll();
    }

    public boolean containsOrder(String id) {
        return this.orderManager.containsOrder(id);
    }

    public String generateUniqueOrderId() {
        return this.orderManager.generateUniqueOrderId();
    }

    public Order buildOrder(String orderId, UUID userId, List<Item> itemsToBuy, Function<String, ParcelCarrier> parcelCarrierSupplier, Supplier<VintageDate> currentDateSupplier, ExpressionSolver expressionSolver) {
        return this.orderFactory.buildOrder(orderId, userId, itemsToBuy, parcelCarrierSupplier, currentDateSupplier, expressionSolver);
    }
}
