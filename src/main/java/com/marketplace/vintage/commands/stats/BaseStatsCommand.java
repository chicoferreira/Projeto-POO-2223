package com.marketplace.vintage.commands.stats;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.utils.VintageDate;

import java.util.function.Predicate;

public abstract class BaseStatsCommand extends BaseCommand {

    private final Vintage vintage;

    public BaseStatsCommand(Vintage vintage, String name, String usage, int minArgs, String description) {
        super(name, usage, minArgs, description);
        this.vintage = vintage;
    }

    protected Vintage getVintage() {
        return vintage;
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
