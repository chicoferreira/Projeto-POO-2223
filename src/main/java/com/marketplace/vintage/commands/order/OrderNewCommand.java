package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;

import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

import java.util.List;

public class OrderNewCommand extends BaseCommand {

    private final UserView userView;

    public OrderNewCommand(UserView userView) {
        super("new", "new order", 0, "Start a new order");
        this.userView = userView;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        currentLoggedInUser.startNewOrder();
    }
}
