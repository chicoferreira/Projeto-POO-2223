package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.command.ParentCommand;

import java.util.List;

public class ParcelCarrierCommand extends ParentCommand {

    public ParcelCarrierCommand(VintageController vintageController, String defaultPriceExpression, List<String> priceExpressionVariables) {
        super("carrier", "Parcel Carrier commands");
        registerCommand(new ParcelCarrierCreateCommand(vintageController, defaultPriceExpression, priceExpressionVariables));
        registerCommand(new ParcelCarrierInfoCommand(vintageController));
        registerCommand(new ParcelCarrierListCommand(vintageController));
        registerCommand(new ParcelCarrierEditCommand(vintageController, priceExpressionVariables));
    }
}
