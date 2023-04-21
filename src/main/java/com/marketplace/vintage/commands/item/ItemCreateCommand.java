package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

public class ItemCreateCommand extends BaseCommand {

    public ItemCreateCommand() {
        super("create", "create", 0, "Create a new item");
    }

    @Override
    public void execute(Logger logger, String[] args) {
        String itemType = getInputPrompter().askForInput(logger, "Enter item type: ");
        logger.info("Creating item of type: " + itemType);
    }
}
