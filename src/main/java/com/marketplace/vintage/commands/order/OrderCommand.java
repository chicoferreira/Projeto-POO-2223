package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.view.impl.UserView;

public class OrderCommand extends ParentCommand {

    public OrderCommand(UserView userView, VintageTimeManager vintageTimeManager, ItemManager itemManager, OrderManager orderManager) {
        super("order", "Order commands");
        registerCommand(new OrderListCommand(itemManager, orderManager, userView, vintageTimeManager));
    }

}
