package com.marketplace.vintage.commands.item.stock;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.view.impl.UserView;

public class ItemStockCommand extends ParentCommand {
    public ItemStockCommand(ParentCommand parentCommand, UserView userView, Vintage vintage) {
        super(parentCommand, "stock", "Stock management commands");
        registerCommand(new ItemStockAddCommand(userView, vintage));
        registerCommand(new ItemStockRemoveCommand(userView, vintage));
    }
}
