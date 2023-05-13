package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

public class ShoppingCartRemoveItemCommand extends BaseCommand {

    private final UserView userView;
    private final VintageController vintageController;

    public ShoppingCartRemoveItemCommand(UserView userView, VintageController vintageController) {
        super("remove", "cart remove <item>", 1, "Remove the given item from the shopping cart");
        this.userView = userView;
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String itemId = args[0];

        User currentLoggedInUser = userView.getCurrentLoggedInUser();

        if (!currentLoggedInUser.getShoppingCart().contains(itemId)) {
            logger.warn("That item isn't in your shopping cart.");
            return;
        }

        this.vintageController.removeItemFromShoppingCart(currentLoggedInUser, itemId);
        logger.info("The item (" + itemId + ") was removed from your shopping cart.");
    }
}
