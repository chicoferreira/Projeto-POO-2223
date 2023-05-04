package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

import java.util.List;

public class ShoppingCartRemoveItemCommand extends BaseCommand {

    private final UserView userView;

    public ShoppingCartRemoveItemCommand(UserView userView) {
        super("remove", "cart remove <item>", 1, "Remove the given item from the shopping cart");
        this.userView = userView;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String itemId = args[0];

        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        List<String> itemsInShoppingCart = currentLoggedInUser.getShoppingCart();

        if (!itemsInShoppingCart.contains(itemId)) {
            logger.warn("This item isn't in your shopping cart");
            return;
        }

        itemsInShoppingCart.remove(itemId);
        logger.info("The item (" + itemId + ") was removed successfully");
    }
}
