package com.marketplace.vintage.commands.stats;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

import java.math.BigDecimal;

public class VintageTotalStatsCommand extends BaseStatsCommand {
    public VintageTotalStatsCommand(Vintage vintage) {
        super(vintage, "vintage", "stats vintage", 0, "Shows the total gained from Vintage");
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        BigDecimal vintageProfit = getVintage().getVintageProfit();
        logger.info("Total gained in Vintage from taxes: " + StringUtils.formatCurrency(vintageProfit));
    }
}
