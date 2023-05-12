package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;

public class AdminOrderCommand extends ParentCommand {
    public AdminOrderCommand(VintageController vintageController) {
        super("order", "Order related commands");
        registerCommand(new AdminOrderInspectCommand(vintageController));
        registerCommand(new AdminOrderListSellerCommand(vintageController));
    }
}
