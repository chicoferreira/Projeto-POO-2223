package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.commands.item.stock.ItemStockCommand;
import com.marketplace.vintage.view.impl.UserView;

public class ItemCommand extends ParentCommand {

    public ItemCommand(UserView userView, Vintage vintage) {
        super("item", "Item management commands");
        registerCommand(new ItemCreateCommand(userView, vintage));
        registerCommand(new ItemListCommand(userView, vintage));
        registerCommand(new ItemInspectCommand(vintage));
        registerCommand(new ItemListAllCommand(vintage));
        registerCommand(new ItemStockCommand(this, userView, vintage));
    }

}
