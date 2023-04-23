package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierFactory;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

public class ParcelCarrierCreatePremiumCommand extends BaseCommand {

    private final ParcelCarrierManager parcelCarrierManager;

    public ParcelCarrierCreatePremiumCommand(ParcelCarrierManager parcelCarrierManager) {
        super("createpremium", "carrier createpremium <carrier name> (price formula)", 1, "Create a new parcel carrier");
        this.parcelCarrierManager = parcelCarrierManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String parcelCarrierName = args[0];

        ParcelCarrier parcelCarrier = ParcelCarrierFactory.createPremiumParcelCarrier(parcelCarrierName);

        // TODO: Ask for price formula

        try {
            parcelCarrierManager.registerParcelCarrier(parcelCarrier);
        } catch (Exception e) {
            logger.warn("Failed to create premium parcel carrier: " + e.getMessage());
            return;
        }

        logger.info("Premium parcel carrier " + parcelCarrier.getName() + " created successfully");
    }
}
