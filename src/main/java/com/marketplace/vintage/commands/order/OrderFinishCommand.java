package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

import java.math.BigDecimal;
import java.util.List;

public class OrderFinishCommand extends BaseCommand {

    private final OrderManager orderManager;
    private final ItemManager itemManager;
    private final UserView userView;
    private VintageTimeManager vintageTimeManager;

    public OrderFinishCommand(OrderManager orderManager, ItemManager itemManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("finish", "finish order", 0, "Finishes the current order");
        this.orderManager = orderManager;
        this.itemManager = itemManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        Order currentOrder = currentLoggedInUser.getCurrentOrder();
        List<Order> ordersMadeByUser = currentLoggedInUser.getOrdersMade();

        BigDecimal orderTotal = currentOrder.calculateTotalPrice(this.itemManager, vintageTimeManager.getCurrentYear());
        logger.info("The total value to pay is " + orderTotal.toString());
        String proceed = getInputPrompter().askForInput(logger, "Do you want to proceed with the order: (Y/n)", String::toLowerCase);

        if(proceed.equals("n")) {
            logger.warn("Did not finish the order");
            return;
        }

        currentOrder.setOrdered();
        orderManager.registerOrder(currentOrder);
        ordersMadeByUser.add(currentOrder);
    }
}
