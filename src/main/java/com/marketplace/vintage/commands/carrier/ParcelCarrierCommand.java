package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.ParentCommand;

public class ParcelCarrierCommand extends ParentCommand {

    public ParcelCarrierCommand(ParcelCarrierManager parcelCarrierManager) {
        super("carrier", "Parcel Carrier commands");
        registerCommand(new ParcelCarrierCreateCommand(parcelCarrierManager));
        registerCommand(new ParcelCarrierCreatePremiumCommand(parcelCarrierManager));
        registerCommand(new ParcelCarrierListCommand(parcelCarrierManager));
    }
}
