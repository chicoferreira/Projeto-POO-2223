package com.marketplace.vintage.commands.stats;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

import java.math.BigDecimal;

public class TopParcelCarrierStatsCommand extends BaseStatsCommand {

    public TopParcelCarrierStatsCommand(Vintage vintage) {
        super(vintage, "topparcelcarrier", "stats topparcelcarrier", 0, "Shows the best parcel carrier");
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        ParcelCarrier parcelCarrierWithMoreMoneyReceived = getVintage().getParcelCarrierWithMoreMoneyReceived();

        if (parcelCarrierWithMoreMoneyReceived == null) {
            logger.warn("No parcel carrier found.");
            return;
        }

        BigDecimal total = getVintage().getParcelCarrierReceivedMoney(parcelCarrierWithMoreMoneyReceived);

        logger.info("Top parcel carrier is " + parcelCarrierWithMoreMoneyReceived.getName() + " with " + StringUtils.formatCurrency(total) + " received.");
    }
}
