package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

public class ShoppingCartAddItemCommand extends BaseCommand {

    private final ItemManager itemManager;
    private final UserView userView;
    private final VintageTimeManager vintageTimeManager;

    public ShoppingCartAddItemCommand(ItemManager itemManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("add", "cart add <item>", 1, "Adds the item given to the User's shopping cart.");
        this.itemManager = itemManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String itemId = args[0];
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        int currentYear = vintageTimeManager.getCurrentYear();

        if (!itemManager.containsItemById(itemId)) {
            logger.warn("Item with the Id " + itemId + " does not exist");
            return;
        }

        Item item = itemManager.getItem(itemId);

        if (item.getOwnerUuid().equals(currentLoggedInUser.getId())) {
            logger.warn("You cannot add your own item to the shopping cart.");
            return;
        }

        logger.info(StringUtils.printItem(item, currentYear));

        boolean proceed = getInputPrompter().askForInput(logger, "Do you want to add this item to the shopping cart? (y/n)", InputMapper.BOOLEAN);
        if (!proceed) return;

        currentLoggedInUser.addItemToShoppingCart(itemId);
        logger.info("Item (" + itemId + ") added to the shopping cart.");
    }
}
