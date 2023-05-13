package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.List;

public class ShoppingCartListCommand extends BaseCommand {

    private final UserView userView;
    private final VintageController vintageController;

    public ShoppingCartListCommand(UserView userView, VintageController vintageController) {
        super("list", "cart list", 0, "Lists the items in shopping cart");
        this.userView = userView;
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        List<String> shoppingCart = currentLoggedInUser.getShoppingCart();

        if (shoppingCart.isEmpty()) {
            logger.warn("The shopping cart is empty.");
            return;
        }

        int currentYear = vintageController.getCurrentYear();

        for (String itemId : shoppingCart) {
            Item item = vintageController.getItem(itemId);
            logger.info(" - " + StringUtils.printItem(item, currentYear));
        }
    }
}
