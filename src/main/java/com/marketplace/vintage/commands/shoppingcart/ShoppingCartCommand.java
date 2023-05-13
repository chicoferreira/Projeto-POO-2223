package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.Vintage;

import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.view.impl.UserView;

public class ShoppingCartCommand extends ParentCommand {

    public ShoppingCartCommand(UserView userView, Vintage vintage) {
        super("cart", "Shopping Cart commands");
        registerCommand(new ShoppingCartAddItemCommand(userView, vintage));
        registerCommand(new ShoppingCartRemoveItemCommand(userView, vintage));
        registerCommand(new ShoppingCartListCommand(userView, vintage));
        registerCommand(new ShoppingCartOrderCommand(userView, vintage));
    }
}
