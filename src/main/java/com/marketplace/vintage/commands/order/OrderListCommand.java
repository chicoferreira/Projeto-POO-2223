package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.Comparator;
import java.util.List;

public class OrderListCommand extends BaseCommand {

    private final UserView userView;
    private final OrderManager orderManager;

    public OrderListCommand(OrderManager orderManager, UserView userView) {
        super("list", "order list", 0, "Lists the orders done by the user");
        this.orderManager = orderManager;
        this.userView = userView;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();

        List<String> ordersMadeByUser = currentLoggedInUser.getCompletedOrderIdsList();

        if (ordersMadeByUser.isEmpty()) {
            logger.warn("You haven't made any orders yet");
        }

        List<Order> sortedOrders = ordersMadeByUser.stream().map(orderManager::getOrder)
                .sorted(Comparator.comparing(Order::getOrderDate))
                .toList();

        for (Order order : sortedOrders) {
            StringUtils.printOrderDisplayFormat(logger, order);
        }
    }
}
