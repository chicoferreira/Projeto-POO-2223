package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;

import java.math.BigDecimal;
import java.util.List;

public class ItemListAllCommand extends BaseCommand {

    private final VintageController vintageController;

    public ItemListAllCommand(VintageController vintageController) {
        super("listall", "item listall", 0, "Lists all items being sold in Vintage");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        List<Item> allItems = vintageController.getAllItems();

        if (allItems.isEmpty()) {
            logger.info("There are no items being sold in Vintage.");
            return;
        }

        logger.info("All items being sold in Vintage:");
        for (Item item : allItems) {
            User owner = vintageController.getUserById(item.getOwnerUuid());
            BigDecimal finalPrice = item.getFinalPrice(vintageController.getCurrentYear());
            logger.info(" - [" + item.getAlphanumericId() + "] " + item.getItemType().getDisplayName() + " - " + item.getDescription() + " from " + owner.getName() + " at " + StringUtils.formatCurrency(finalPrice));
        }
    }
}
