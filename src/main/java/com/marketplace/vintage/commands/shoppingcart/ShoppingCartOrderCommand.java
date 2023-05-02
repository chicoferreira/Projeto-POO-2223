package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.marketplace.vintage.item.condition.ItemConditions.NEW;

public class ShoppingCartOrderCommand extends BaseCommand {

    private final OrderManager orderManager;
    private final ItemManager itemManager;
    private final ParcelCarrierManager parcelCarrierManager;
    private final UserView userView;
    private VintageTimeManager vintageTimeManager;

    public ShoppingCartOrderCommand(OrderManager orderManager, ItemManager itemManager, ParcelCarrierManager parcelCarrierManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("order", "order", 0, "Makes an order out of the current shopping cart");
        this.orderManager = orderManager;
        this.itemManager = itemManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        List<String> currentOrder = currentLoggedInUser.getShoppingCart();

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
                valueOfItem = valueOfItem.add(BigDecimal.valueOf(0.25));
            }

            valueOfItem = valueOfItem.add(BigDecimal.valueOf(0.25));
            orderTotal = orderTotal.add(valueOfItem);

            String message = StringUtils.printItem(currentOrder.get(i), itemManager, currentYear, parcelCarrierManager);

            logger.info(" - " + message );
        }

        logger.info("The total value to pay is " + orderTotal);
        String proceed = getInputPrompter().askForInput(logger, "Do you want to proceed with the order: (Y/n)", String::toLowerCase);

        if(proceed.equals("n")) {
            logger.warn("Did not finish the order");
            return;
        }

        Order newOrder = new Order(currentLoggedInUser.getId(), orderTotal, (ArrayList<String>) currentOrder);
        this.orderManager.registerOrder(newOrder);
        currentLoggedInUser.addOrder(newOrder);
        logger.info("Order (" + newOrder.getOrderId() + ") has been made");

        currentLoggedInUser.cleanShoppingCart();

    }
}
