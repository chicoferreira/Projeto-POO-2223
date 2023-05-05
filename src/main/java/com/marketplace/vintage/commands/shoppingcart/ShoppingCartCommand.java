package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.VintageTimeManager;

import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.view.impl.UserView;

public class ShoppingCartCommand extends ParentCommand {

    public ShoppingCartCommand(ItemManager itemManager, VintageController vintageController, UserView userView, VintageTimeManager vintageTimeManager) {
        super("cart", "Shopping Cart commands");
        registerCommand(new ShoppingCartAddItemCommand(itemManager, userView, vintageTimeManager));
        registerCommand(new ShoppingCartRemoveItemCommand(userView));
        registerCommand(new ShoppingCartListCommand(itemManager, userView, vintageTimeManager));
        registerCommand(new ShoppingCartOrderCommand(userView, vintageController));
    }
}
