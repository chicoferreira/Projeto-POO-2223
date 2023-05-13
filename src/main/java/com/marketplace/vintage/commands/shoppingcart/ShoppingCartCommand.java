package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageController;

import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.view.impl.UserView;

public class ShoppingCartCommand extends ParentCommand {

    public ShoppingCartCommand(UserView userView, VintageController vintageController) {
        super("cart", "Shopping Cart commands");
        registerCommand(new ShoppingCartAddItemCommand(userView, vintageController));
        registerCommand(new ShoppingCartRemoveItemCommand(userView, vintageController));
        registerCommand(new ShoppingCartListCommand(userView, vintageController));
        registerCommand(new ShoppingCartOrderCommand(userView, vintageController));
    }
}
