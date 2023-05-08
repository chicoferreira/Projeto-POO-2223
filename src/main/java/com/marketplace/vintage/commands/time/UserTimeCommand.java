package com.marketplace.vintage.commands.time;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

public class UserTimeCommand extends BaseCommand {

    private final VintageController vintageController;

    public UserTimeCommand(VintageController vintageController) {
        super("time", "time", 0, "Displays the current time");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        logger.info("The current time is: " + vintageController.getCurrentDate());
    }
}
