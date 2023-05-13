package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

public class ShoppingCartAddItemCommand extends BaseCommand {

    private final UserView userView;
    private final Vintage vintage;

    public ShoppingCartAddItemCommand(UserView userView, Vintage vintage) {
        super("add", "cart add <itemId>", 1, "Adds the item given to the User's shopping cart.");
        this.userView = userView;
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String itemId = args[0];
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        int currentYear = vintage.getCurrentYear();

        if (!vintage.containsItemById(itemId)) {
            logger.warn("Item with the id " + itemId + " does not exist.");
            return;
        }

        Item item = vintage.getItem(itemId);

        if (item.getOwnerUuid().equals(currentLoggedInUser.getId())) {
            logger.warn("You cannot add your own item to the shopping cart.");
            return;
        }

        logger.info(StringUtils.printItem(item, currentYear));

        boolean proceed = inputPrompter.askForInput(logger, "Do you want to add this item to the shopping cart? (y/n)", InputMapper.BOOLEAN);
        if (!proceed) return;

        vintage.addItemToShoppingCart(currentLoggedInUser, itemId);
        logger.info("Item (" + itemId + ") added to the shopping cart.");
    }
}
