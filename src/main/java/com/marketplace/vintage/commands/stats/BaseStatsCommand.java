package com.marketplace.vintage.commands.stats;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.utils.VintageDate;

import java.util.function.Predicate;

public abstract class BaseStatsCommand extends BaseCommand {

    private final VintageController vintageController;

    public BaseStatsCommand(VintageController vintageController, String name, String usage, int minArgs, String description) {
        super(name, usage, minArgs, description);
        this.vintageController = vintageController;
    }

    protected VintageController getVintageController() {
        return vintageController;
    }

    protected Predicate<VintageDate> getDatePredicate(String[] args) {
        return getDatePredicate(args, 0);
    }

    protected Predicate<VintageDate> getDatePredicate(String[] args, int offset) {
        if (args.length < 2 + offset) {
            return date -> true;
        }
        return date -> date.isBetweenInclusive(VintageDate.fromString(args[offset]), VintageDate.fromString(args[1 + offset]));
    }
}
