package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

public class ItemAddToShoppingCartCommand extends BaseCommand {

    private final ItemManager itemManager;
    private final UserView userView;
    private final VintageTimeManager vintageTimeManager;

    public ItemAddToShoppingCartCommand(ItemManager itemManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("add", "add", 1, "Adds the item given to the User's shopping Cart.");
        this.itemManager = itemManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String itemId = args[0];
        User currentLoggedInUser = userView.getCurrentLoggedInUser();

        if (!itemManager.containsItemById(itemId)) {
            logger.warn("Item with the Id " + itemId + " does not exist");
            return;
        }

        Item item = itemManager.getItem(itemId);
        logger.info(" - Type: " + item.getItemType());
        logger.info(" - Brand: " + item.getBrand());
        logger.info(" - Base Price: " + item.getBasePrice());
        logger.info(" - Final Price: " + item.getFinalPrice(vintageTimeManager.getCurrentYear()));
        String proceed = getInputPrompter().askForInput(logger, "Do you want to add this item to the shopping cart? (Y/n) ", String::toLowerCase);

        if(proceed.equals("n")) {
            logger.info("This item wasn't added to the shopping cart");
            return;
        }
        currentLoggedInUser.addItemToShoppingCart(itemId);
    }
}
