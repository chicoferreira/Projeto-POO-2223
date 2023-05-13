package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.invoice.InvoiceLine;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.List;

public class ShoppingCartOrderCommand extends BaseCommand {

    private final UserView userView;
    private final VintageController vintageController;

    public ShoppingCartOrderCommand(UserView userView, VintageController vintageController) {
        super("order", "cart order (customId)", 0, "Makes an order out of the current shopping cart");
        this.userView = userView;
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        List<String> currentOrder = currentLoggedInUser.getShoppingCart();

        if (currentOrder.isEmpty()) {
            logger.warn("You haven't added any items to the shopping cart.");
            return;
        }

        for (String s : currentOrder) {
            if (!vintageController.itemHasStock(s)) {
                logger.warn("The item (" + s + ") has no stock. Please remove it from your shopping cart.");
                return;
            }
        }

        String customId = args.length > 0 ? args[0] : null;
        Order order = vintageController.assembleOrder(customId, currentLoggedInUser);

        logger.info("Order summary:");

        for (InvoiceLine invoiceLine : order.getInvoiceLines()) {
            logger.info(" - " + StringUtils.formatCurrency(invoiceLine.getPrice()) + ": " + invoiceLine.getDisplayName());
        }
        logger.info("Total: " + StringUtils.formatCurrency(order.getTotalPrice()));

        logger.info();

        boolean proceed = inputPrompter.askForInput(logger, "Confirm the purchase (y/n)", InputMapper.BOOLEAN);

        if (!proceed) {
            logger.info("Order creation cancelled.");
            return;
        }

        logger.info("Order (" + order.getOrderId() + ") created successfully. Your order will be delivered soon.");
        this.vintageController.registerOrder(order, currentLoggedInUser);
    }
}
