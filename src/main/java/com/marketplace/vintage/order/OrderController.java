package com.marketplace.vintage.order;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.utils.VintageDate;

import java.util.List;

public class OrderController {

    private final OrderManager orderManager;

    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void notifyTimeChange(VintageDate newDate) {
        List<Order> status = orderManager.getAllWithStatus(OrderStatus.ORDERED);
        for (Order order : status) {
            if (order.getOrderDate().plusDays(VintageConstants.DAYS_TO_DELIVER_ORDER).isBeforeOrSame(newDate)) {
                setOrderDelivered(order);
            }
        }
    }

    public void setOrderDelivered(Order order) {
        order.setOrderStatus(OrderStatus.DELIVERED);
    }

}
