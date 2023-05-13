package com.marketplace.vintage.commands.item.stock;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.view.impl.UserView;

public class ItemStockAddCommand extends ItemStockChangeCommand {

    public ItemStockAddCommand(UserView userView, Vintage vintage) {
        super(userView, vintage, "add", "item stock add <item> <stock>", "Adds stock to an item");
    }

    @Override
    protected void operation(Logger logger, Item item, int stock) {
        int newStock = getVintage().addItemStock(item, stock);
        logger.info("Added " + stock + " stock to item '" + item.getAlphanumericId() + "'. New stock: " + newStock);
    }
}
