package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

public class ParcelCarrierInfoCommand extends BaseCommand {

    private final VintageController vintageController;

    public ParcelCarrierInfoCommand(VintageController vintageController) {
        super("info", "carrier info <name>", 1, "Get information about a parcel carrier");
        this.vintageController = vintageController;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String name = args[0];

        if (!vintageController.containsCarrierByName(name)) {
            logger.warn("Parcel carrier with name " + name + " does not exist");
            return;
        }

        ParcelCarrier parcelCarrier = vintageController.getCarrierByName(name);
        logger.info("Name: " + name);
        logger.info("Type: " + parcelCarrier.getType());
        logger.info("Expedition price expression: " + parcelCarrier.getExpeditionPriceExpression());
    }
}
