package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.logging.Logger;

public class ParcelCarrierInfoCommand extends BaseCommand {

    private final ParcelCarrierManager parcelCarrierManager;

    public ParcelCarrierInfoCommand(ParcelCarrierManager parcelCarrierManager) {
        super("info", "carrier info <name>", 1, "Get information about a parcel carrier");
        this.parcelCarrierManager = parcelCarrierManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String name = args[0];

        try {
            ParcelCarrier parcelCarrier = parcelCarrierManager.getCarrierByName(name);
            logger.info("Name: " + name);
            logger.info("Type: " + parcelCarrier.getType());
            logger.info("Expedition price expression: " + parcelCarrier.getExpeditionPriceExpression());
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
        }

    }
}
