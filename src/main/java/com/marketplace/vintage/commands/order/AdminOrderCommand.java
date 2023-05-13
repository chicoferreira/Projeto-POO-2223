package com.marketplace.vintage.commands.order;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;

public class AdminOrderCommand extends ParentCommand {
    public AdminOrderCommand(Vintage vintage) {
        super("order", "Order related commands");
        registerCommand(new AdminOrderInspectCommand(vintage));
        registerCommand(new AdminOrderListSellerCommand(vintage));
    }
}
