package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderListCommand extends BaseCommand {

    private final UserView userView;
    private final OrderManager orderManager;

    public OrderListCommand(OrderManager orderManager, UserView userView) {
        super("list", "list", 0, "Lists the orders done by the user");
        this.orderManager = orderManager;
        this.userView = userView;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();

        List<UUID> ordersMadeByUser = currentLoggedInUser.getOrdersMade();

        if(ordersMadeByUser.isEmpty()) {
            logger.warn("You haven't made any orders yet");
        }

        for(int i = 0; i < ordersMadeByUser.size(); i++) {
            UUID indexedOrderId = ordersMadeByUser.get(i);
            Order indexedOrder = orderManager.getOrder(indexedOrderId);

            logger.info("Order " + i+1 +"# - " + indexedOrderId + " :");
            ArrayList<String> itemsInOrder = indexedOrder.getItemsInOrder();

            for(int j = 0; j < itemsInOrder.size(); i++ ) {
                String indexedItem = itemsInOrder.get(j);
                logger.info(" - Item " + j+1 + "# - ID: " + indexedItem);
            }
            logger.info("Paid Total: " + indexedOrder.getTotalPrice());
        }

    }
}
