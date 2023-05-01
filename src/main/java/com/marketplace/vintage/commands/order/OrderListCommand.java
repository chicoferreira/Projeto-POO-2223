package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderListCommand extends BaseCommand {

    private final ItemManager itemManager;
    private final UserView userView;
    private final OrderManager orderManager;
    private final VintageTimeManager vintageTimeManager;

    public OrderListCommand(ItemManager itemManager, OrderManager orderManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("list", "list", 0, "Lists the orders done by the user");
        this.itemManager = itemManager;
        this.orderManager = orderManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
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
            List<String> itemsInOrder = indexedOrder.getItemsInOrder();

            for(int j = 0; j < itemsInOrder.size(); j++ ) {
                String indexedItem = itemsInOrder.get(j);
                logger.info(" - Item " + j + "# - ID: " + indexedItem);
            }
            logger.info("Paid Total: " + indexedOrder.calculateTotalPrice(itemManager, vintageTimeManager.getCurrentYear()));
            logger.info("Status: " + indexedOrder.getOrderState());
        }

    }
}
