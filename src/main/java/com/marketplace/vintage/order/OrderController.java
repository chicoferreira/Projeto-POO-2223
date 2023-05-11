package com.marketplace.vintage.order;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.VintageDate;

import java.util.List;

public class OrderController {

    private final OrderManager orderManager;

    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void notifyTimeChange(Logger logger, VintageDate newDate) {
        int ordersChanged = 0;
        List<Order> status = orderManager.getAllWithStatus(OrderStatus.ORDERED);
        for (Order order : status) {
            if (order.getOrderDate().plusDays(VintageConstants.DAYS_TO_DELIVER_ORDER).isBeforeOrSame(newDate)) {
                setOrderDelivered(order);
                ordersChanged++;
            }
        }

        if (ordersChanged > 0) {
            logger.info("Delivered " + ordersChanged + " orders.");
        }
    }

    public void setOrderDelivered(Order order) {
        order.setOrderStatus(OrderStatus.DELIVERED);
    }

}
