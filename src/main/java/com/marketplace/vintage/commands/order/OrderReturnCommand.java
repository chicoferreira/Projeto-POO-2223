package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

public class OrderReturnCommand extends BaseCommand {
    private final Vintage vintage;
    private final UserView userView;

    public OrderReturnCommand(UserView userView, Vintage vintage) {
        super("return", "order return <orderId>", 1, "Returns a delivered order");
        this.userView = userView;
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        User user = this.userView.getCurrentLoggedInUser();
        Order order = this.vintage.getOrder(args[0]);

        if (!order.getBuyerId().equals(user.getId())) {
            logger.warn("You can only return your own orders.");
            return;
        }

        if (!this.vintage.isOrderReturnable(order)) {
            logger.warn("Order " + order.getOrderId() + " is not returnable. " +
                    "You can only return within two days after the order has been delivered.");
            return;
        }

        this.vintage.returnOrder(order);
        logger.info("Order " + order.getOrderId() + " returned successfully");
    }
}
