package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;

public class OrderReturnCommand extends BaseCommand {
    private final VintageController vintageController;

    public OrderReturnCommand(VintageController vintageController) {
        super("return", "order return <orderId>", 1, "Returns a delivered order");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        Order order = this.vintageController.getOrder(args[0]);
        if (!this.vintageController.isOrderReturnable(order)) {
            logger.warn("Order " + order.getOrderId() + " is not returnable. " +
                    "You can only return within two days after the order has been delivered.");
            return;
        }

        this.vintageController.returnOrder(order);
        logger.info("Order " + order.getOrderId() + " returned successfully");
    }
}
