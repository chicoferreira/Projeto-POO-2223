package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.UUID;

public class ShoppingCartAddItemCommand extends BaseCommand {

    private final ItemManager itemManager;
    private final ParcelCarrierManager parcelCarrierManager;
    private final UserView userView;
    private final VintageTimeManager vintageTimeManager;

    public ShoppingCartAddItemCommand(ItemManager itemManager, ParcelCarrierManager parcelCarrierManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("add", "add", 1, "Adds the item given to the User's shopping cart.");
        this.itemManager = itemManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
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

        String message = StringUtils.printItem(itemId, itemManager, currentYear, parcelCarrierManager);
        logger.info(message);

        boolean proceed = getInputPrompter().askForInput(logger, "Do you want to add this item to the shopping cart? (y/n) ", InputMapper.BOOLEAN);
        if (!proceed) return;

        currentLoggedInUser.addItemToShoppingCart(itemId);
        logger.info("Item (" + itemId + ") added to the shopping cart successfully");
    }
}
