package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.ParentCommand;

import java.util.List;

public class ParcelCarrierCommand extends ParentCommand {

    public ParcelCarrierCommand(Vintage vintage, String defaultPriceExpression, List<String> priceExpressionVariables) {
        super("carrier", "Parcel carrier related commands");
        registerCommand(new ParcelCarrierCreateCommand(vintage, defaultPriceExpression, priceExpressionVariables));
        registerCommand(new ParcelCarrierInfoCommand(vintage));
        registerCommand(new ParcelCarrierListCommand(vintage));
        registerCommand(new ParcelCarrierEditCommand(vintage, priceExpressionVariables));
    }
}
