package com.marketplace.vintage.commands;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.command.BaseCommand;

import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.impl.UserView;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartCommand extends BaseCommand {

    private final ItemManager itemManager;
    private final UserView userView;
    private final VintageTimeManager vintageTimeManager;

    public ShoppingCartCommand(ItemManager itemManager, UserView userView, VintageTimeManager vintageTimeManager) {
        super("shoppingcart", "shoppingcart", 0 , "Lists the Items in the Shopping Cart");
        this.itemManager = itemManager;
        this.userView = userView;
        this.vintageTimeManager = vintageTimeManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        User currentLoggedInUser = userView.getCurrentLoggedInUser();

        List<String> shoppingCart = currentLoggedInUser.getShoppingCart();

        BigDecimal totalCartValue = BigDecimal.valueOf(0);
        logger.info("User's Shopping Cart: ");
        for(int i = 0; i < shoppingCart.size(); i++) {
            BigDecimal itemPrice = itemManager.getItem(shoppingCart.get(i)).getFinalPrice(vintageTimeManager.getCurrentYear());
            totalCartValue = totalCartValue.add(itemPrice);
            logger.info(" - Item " + i +"# - ID: " + shoppingCart.get(i) + " - Price: " + itemPrice );
        }

        logger.info("Total: " + totalCartValue);
        currentLoggedInUser.cleanShoppingCart();
    }
}
