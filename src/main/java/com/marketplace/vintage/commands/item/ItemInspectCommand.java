package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.logging.Logger;

public class ItemInspectCommand extends BaseCommand {

    private final VintageController vintageController;

    public ItemInspectCommand(VintageController vintageController) {
        super("inspect", "item inspect <itemId>", 1, "Shows the details of an item");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String itemId = args[0];

        if (!vintageController.existsItem(itemId)) {
            logger.warn("Item " + itemId + " does not exist.");
            return;
        }

        Item item = vintageController.getItem(itemId);

        logger.info("Item " + itemId + " details:");
        for (ItemProperty requiredProperty : item.getItemType().getRequiredProperties()) {
            logger.info(" - " + requiredProperty.name() + ": " + item.getProperty(requiredProperty, Object.class).toString());
        }
    }
}
