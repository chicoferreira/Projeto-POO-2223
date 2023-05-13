package com.marketplace.vintage.commands.time;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

public class CurrentTimeCommand extends BaseCommand {

    private final Vintage vintage;

    // Usage is parameterized to allow for different usages in different views
    public CurrentTimeCommand(Vintage vintage, String usage) {
        super("time", usage, 0, "Displays the current time");
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        logger.info("The current time is: " + vintage.getCurrentDate());
    }
}
