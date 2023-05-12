package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderStatus;
import com.marketplace.vintage.order.invoice.InvoiceLine;
import com.marketplace.vintage.utils.StringUtils;

public class AdminOrderInspectCommand extends BaseCommand {

    private final VintageController vintageController;

    public AdminOrderInspectCommand(VintageController vintageController) {
        super("inspect", "order inspect <orderId>", 1, "Inspect an order by its id");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String orderId = args[0];

        if (!vintageController.existsOrder(orderId)) {
            logger.warn("Order " + orderId + " does not exist.");
            return;
        }

        Order order = vintageController.getOrder(orderId);
        logger.info(order.getOrderId() + " summary:");

        for (InvoiceLine invoiceLine : order.getInvoiceLines()) {
            logger.info(" - " + StringUtils.formatCurrency(invoiceLine.getPrice()) + ": " + invoiceLine.getDisplayName());
        }
        logger.info("Total: " + StringUtils.formatCurrency(order.getTotalPrice()));
        logger.info("Ordered Date: " + order.getOrderDate());
        logger.info("Status: " + order.getOrderStatus());

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            logger.info("Deliver Date: " + order.getDeliverDate());
        }
    }
}
