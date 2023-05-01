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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.marketplace.vintage.item.condition.ItemConditions.NEW;

public class OrderFinishCommand extends BaseCommand {

    private final OrderManager orderManager;
    private final ItemManager itemManager;
    private final UserView userView;
    private VintageTimeManager vintageTimeManager;

    public OrderFinishCommand(OrderManager orderManager, ItemManager itemManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("finish", "finish", 0, "Finishes the current order");
        this.orderManager = orderManager;
        this.itemManager = itemManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        List<String> currentOrder = currentLoggedInUser.getShoppingCart();
        List<UUID> ordersMadeByUser = currentLoggedInUser.getOrdersMade();

        if(currentOrder.isEmpty()) {
            logger.warn("You haven't added any items to the shopping cart");
            return;
        }

        int numberOfItems = currentOrder.size();
        int currentYear = vintageTimeManager.getCurrentYear();

        BigDecimal orderTotal = BigDecimal.valueOf(0);

        for(int i = 0; i < numberOfItems; i++ ) {
            Item indexedItem = itemManager.getItem(currentOrder.get(i));
            BigDecimal valueOfItem = indexedItem.getFinalPrice(currentYear);

            if(indexedItem.getItemCondition() == NEW) {
                valueOfItem.add(BigDecimal.valueOf(0.25));
            }

            valueOfItem.add(BigDecimal.valueOf(0.25));
            orderTotal.add(valueOfItem);

            logger.info(" - " + indexedItem.getItemType() + " " + indexedItem.getBrand() + " - " + valueOfItem );
        }

        logger.info("The total value to pay is " + orderTotal);
        String proceed = getInputPrompter().askForInput(logger, "Do you want to proceed with the order: (Y/n)", String::toLowerCase);

        if(proceed.equals("n")) {
            logger.warn("Did not finish the order");
            return;
        }

        Order newOrder = new Order(currentLoggedInUser.getId(), orderTotal, (ArrayList<String>) currentOrder);
        this.orderManager.registerOrder(newOrder);
        ordersMadeByUser.add(newOrder.getOrderId());
    }
}
