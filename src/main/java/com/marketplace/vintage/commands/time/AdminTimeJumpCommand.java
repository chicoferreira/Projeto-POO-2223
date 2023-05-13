package com.marketplace.vintage.commands.time;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.VintageDate;

public class AdminTimeJumpCommand extends BaseCommand {

    private final Vintage vintage;

    public AdminTimeJumpCommand(Vintage vintage) {
        super("jump", "time jump <days>", 1, "Jump the time by the specified number of days");
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        int days;
        try {
            days = InputMapper.ofIntRange(1, Integer.MAX_VALUE).apply(args[0]);
        } catch (IllegalArgumentException e) {
            logger.warn("Number of days must be a positive integer. Usage: " + getUsage());
            return;
        }

        VintageDate beforeDate = vintage.getCurrentDate();
        VintageDate afterDate = vintage.jumpTime(logger, days);

        logger.info("Time jumped from " + beforeDate + " to " + afterDate + ".");
    }
}
