package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.commands.item.stock.ItemStockCommand;
import com.marketplace.vintage.view.impl.UserView;

public class ItemCommand extends ParentCommand {

    public ItemCommand(UserView userView, VintageController vintageController) {
        super("item", "Item management commands");
        registerCommand(new ItemCreateCommand(userView, vintageController));
        registerCommand(new ItemListCommand(userView, vintageController));
        registerCommand(new ItemInspectCommand(vintageController));
        registerCommand(new ItemListAllCommand(vintageController));
        registerCommand(new ItemStockCommand(this, userView, vintageController));
    }

}
