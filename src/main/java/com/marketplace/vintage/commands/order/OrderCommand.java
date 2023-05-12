package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.view.impl.UserView;

public class OrderCommand extends ParentCommand {

    public OrderCommand(UserView userView, VintageController vintageController) {
        super("order", "Order commands");
        registerCommand(new OrderListCommand(userView, vintageController));
        registerCommand(new OrderReturnCommand(vintageController));
    }

}
