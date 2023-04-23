package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.carrier.ParcelCarrierType;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;

import java.util.List;

public class ParcelCarrierListCommand extends BaseCommand {
    private final ParcelCarrierManager parcelCarrierManager;

    public ParcelCarrierListCommand(ParcelCarrierManager parcelCarrierManager) {
        super("list", "carrier list", 0, "List all registered parcel carriers");
        this.parcelCarrierManager = parcelCarrierManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        List<ParcelCarrier> all = parcelCarrierManager.getAll();

        if (all.isEmpty()) {
            logger.info("No parcel carriers registered");
            return;
        }

        // Sort by premium first
        all.sort((o1, o2) -> {
            if (o1.getType() == o2.getType()) return 0;
            return o1.getType() == ParcelCarrierType.PREMIUM ? -1 : 1;
        });

        logger.info("Registered parcel carriers:");
        for (ParcelCarrier parcelCarrier : all) {
            if (parcelCarrier.getType() == ParcelCarrierType.PREMIUM) {
                logger.info("\t[PREMIUM] " + parcelCarrier.getName());
            } else {
                logger.info("\t" + parcelCarrier.getName());
            }
        }
    }
}
