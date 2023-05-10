package com.marketplace.vintage.commands.time;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

public class CurrentTimeCommand extends BaseCommand {

    private final VintageController vintageController;

    // Usage is parameterized to allow for different usages in different views
    public CurrentTimeCommand(VintageController vintageController, String usage) {
        super("time", usage, 0, "Displays the current time");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        logger.info("The current time is: " + vintageController.getCurrentDate());
    }
}
