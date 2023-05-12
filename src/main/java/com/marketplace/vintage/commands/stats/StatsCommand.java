package com.marketplace.vintage.commands.stats;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;

public class StatsCommand extends ParentCommand {
    public StatsCommand(VintageController vintageController) {
        super("stats", "Stats related commands");

        registerCommand(new TopSellerStatsCommand(vintageController));
        registerCommand(new TopParcelCarrierStatsCommand(vintageController));
        registerCommand(new VintageTotalStatsCommand(vintageController));
        registerCommand(new TopListStatsCommand(vintageController));
    }
}
