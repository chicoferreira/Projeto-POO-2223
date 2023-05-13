package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

public class ParcelCarrierInfoCommand extends BaseCommand {

    private final Vintage vintage;

    public ParcelCarrierInfoCommand(Vintage vintage) {
        super("info", "carrier info <name>", 1, "Get information about a parcel carrier");
        this.vintage = vintage;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String name = args[0];

        if (!vintage.containsCarrierByName(name)) {
            logger.warn("Parcel carrier with name " + name + " does not exist");
            return;
        }

        ParcelCarrier parcelCarrier = vintage.getCarrierByName(name);
        logger.info("Name: " + name);
        logger.info("Type: " + parcelCarrier.getType());
        logger.info("Expedition price expression: " + parcelCarrier.getExpeditionPriceExpression());
    }
}
