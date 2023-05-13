package com.marketplace.vintage.commands.item.stock;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.view.impl.UserView;

public class ItemStockCommand extends ParentCommand {
    public ItemStockCommand(ParentCommand parentCommand, UserView userView, VintageController vintageController) {
        super(parentCommand, "stock", "Stock management commands");
        registerCommand(new ItemStockAddCommand(userView, vintageController));
        registerCommand(new ItemStockRemoveCommand(userView, vintageController));
    }
}
