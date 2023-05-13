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
}
