package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.view.impl.UserView;

public class OrderCommand extends ParentCommand {

    public OrderCommand(UserView userView, Vintage vintage) {
        super("order", "Order commands");
        registerCommand(new OrderListCommand(userView, vintage));
        registerCommand(new OrderReturnCommand(userView, vintage));
    }

}
