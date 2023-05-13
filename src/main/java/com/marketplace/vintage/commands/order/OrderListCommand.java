package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderStatus;
import com.marketplace.vintage.order.invoice.InvoiceLine;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.Comparator;
import java.util.List;

public class OrderListCommand extends BaseCommand {

    private final UserView userView;
    private final Vintage vintage;

    public OrderListCommand(UserView userView, Vintage vintage) {
        super("list", "order list", 0, "Lists the orders done by the user");
        this.userView = userView;
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();

        List<String> ordersMadeByUser = currentLoggedInUser.getCompletedOrderIdsList();

        if (ordersMadeByUser.isEmpty()) {
            logger.warn("You haven't made any orders yet. Add items to your cart ('cart add') and checkout ('cart order') to make an order.");
            return;
        }

        List<Order> sortedOrders = ordersMadeByUser.stream().map(vintage::getOrder)
                .sorted(Comparator.comparing(Order::getOrderDate))
                .toList();

        logger.info();

        for (Order order : sortedOrders) {
            logger.info(" - " + order.getOrderId() + " summary:");

            for (InvoiceLine invoiceLine : order.getInvoiceLines()) {
                logger.info("   - " + StringUtils.formatCurrency(invoiceLine.getPrice()) + ": " + invoiceLine.getDisplayName());
            }
            logger.info("   Total: " + StringUtils.formatCurrency(order.getTotalPrice()));
            logger.info("   Ordered Date: " + order.getOrderDate());

            if (vintage.isOrderReturnable(order)) {
                logger.info("   Status: " + order.getOrderStatus() + " (returnable)");
            } else {
                logger.info("   Status: " + order.getOrderStatus());
            }

            if (order.getOrderStatus() == OrderStatus.DELIVERED) {
                logger.info("   Deliver Date: " + order.getDeliverDate());
            }

            logger.info();
        }
    }
}
