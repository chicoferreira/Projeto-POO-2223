package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

import java.util.List;

public class ShoppingCartListCommand extends BaseCommand {

    private final ItemManager itemManager;
    private final UserView userView;
    private final VintageTimeManager vintageTimeManager;


    public ShoppingCartListCommand(ItemManager itemManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("list", "list", 0, "Lists the items in shopping cart");
        this.itemManager = itemManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        List<String> shoppingCart = currentLoggedInUser.getShoppingCart();

        if(shoppingCart.isEmpty()) {
            logger.warn("You don't have anything in your shopping cart");
            return;
        }

        for(int i = 0; i < shoppingCart.size(); i++) {
            Item indexedItem = itemManager.getItem(shoppingCart.get(i));
            logger.info("Item #" + i++ +" - ID: " + indexedItem.getAlphanumericId() + " - Base Price: " + indexedItem.getBasePrice());
        }
    }
}
