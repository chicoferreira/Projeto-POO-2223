package com.marketplace.vintage.commands.shoppingcart;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.List;
import java.util.UUID;

public class ShoppingCartListCommand extends BaseCommand {

    private final ItemManager itemManager;
    private final UserView userView;
    private final VintageTimeManager vintageTimeManager;
    private final ParcelCarrierManager parcelCarrierManager;


    public ShoppingCartListCommand(ItemManager itemManager, ParcelCarrierManager parcelCarrierManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("list", "list", 0, "Lists the items in shopping cart");
        this.itemManager = itemManager;
        this.parcelCarrierManager = parcelCarrierManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();
        List<String> shoppingCart = currentLoggedInUser.getShoppingCart();
        int currentYear = vintageTimeManager.getCurrentYear();

        if(shoppingCart.isEmpty()) {
            logger.warn("You don't have anything in your shopping cart");
            return;
        }

        int counter = 1;
        for (String itemId : shoppingCart) {
            String message = StringUtils.printItem(itemId, itemManager, currentYear, parcelCarrierManager);
            logger.info(message);
            counter++;
        }
    }
}
