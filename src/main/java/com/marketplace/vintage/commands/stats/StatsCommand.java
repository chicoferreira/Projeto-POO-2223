package com.marketplace.vintage.commands.stats;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;

public class StatsCommand extends ParentCommand {
    public StatsCommand(Vintage vintage) {
        super("stats", "Stats related commands");

        registerCommand(new TopSellerStatsCommand(vintage));
        registerCommand(new TopParcelCarrierStatsCommand(vintage));
        registerCommand(new VintageTotalStatsCommand(vintage));
        registerCommand(new TopListStatsCommand(vintage));
    }
}
