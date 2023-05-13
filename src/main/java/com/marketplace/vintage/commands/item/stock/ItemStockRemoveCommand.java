package com.marketplace.vintage.commands.item.stock;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.view.impl.UserView;

public class ItemStockRemoveCommand extends ItemStockChangeCommand {

    public ItemStockRemoveCommand(UserView userView, VintageController vintageController) {
        super(userView, vintageController, "remove", "item stock remove <item> <stock>", "Removes stock from an item");
    }

    @Override
    protected void operation(Logger logger, Item item, int stock) {
        int newStock = getVintageController().removeItemStock(item, stock);
        logger.info("Removed " + stock + " stock from item '" + item.getAlphanumericId() + "'. New stock: " + newStock);
    }
}
