package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.command.BaseCommand;

import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.commands.shoppingcart.ShoppingCartRemoveItemCommand;
import com.marketplace.vintage.commands.shoppingcart.ShoppingCartAddItemCommand;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartCommand extends ParentCommand {

    public ShoppingCartCommand(ItemManager itemManager, OrderManager orderManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("shoppingcart", "Shopping Cart commands");
        registerCommand(new ShoppingCartAddItemCommand(itemManager, userView, vintageTimeManager));
        registerCommand(new ShoppingCartRemoveItemCommand(userView));
        registerCommand(new ShoppingCartListCommand(itemManager, userView, vintageTimeManager));
        registerCommand(new ShoppingCartOrderCommand(orderManager, itemManager, userView, vintageTimeManager));

    }
}
