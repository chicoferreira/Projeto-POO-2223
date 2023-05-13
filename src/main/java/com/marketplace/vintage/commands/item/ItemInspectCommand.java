package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.logging.Logger;

public class ItemInspectCommand extends BaseCommand {

    private final Vintage vintage;

    public ItemInspectCommand(Vintage vintage) {
        super("inspect", "item inspect <itemId>", 1, "Shows the details of an item");
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String itemId = args[0];

        if (!vintage.existsItem(itemId)) {
            logger.warn("Item " + itemId + " does not exist.");
            return;
        }

        Item item = vintage.getItem(itemId);

        logger.info("Item " + itemId + " details:");
        for (ItemProperty requiredProperty : item.getItemType().getRequiredProperties()) {
            logger.info(" - " + requiredProperty.name() + ": " + item.getProperty(requiredProperty, Object.class).toString());
        }
    }
}
