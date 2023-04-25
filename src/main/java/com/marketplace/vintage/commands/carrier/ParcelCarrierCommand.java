package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.ParentCommand;
import com.marketplace.vintage.expression.ExpressionSolver;

public class ParcelCarrierCommand extends ParentCommand {

    public ParcelCarrierCommand(ParcelCarrierManager parcelCarrierManager, ExpressionSolver expressionSolver) {
        super("carrier", "Parcel Carrier commands");
        registerCommand(new ParcelCarrierCreateCommand(parcelCarrierManager, expressionSolver));
        registerCommand(new ParcelCarrierCreatePremiumCommand(parcelCarrierManager));
        registerCommand(new ParcelCarrierListCommand(parcelCarrierManager));
    }
}