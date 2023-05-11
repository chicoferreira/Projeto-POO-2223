package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

import java.util.List;

public class ParcelCarrierEditCommand extends BaseCommand {

    private final VintageController vintageController;
    private final List<String> priceExpressionVariables;

    public ParcelCarrierEditCommand(VintageController vintageController, List<String> priceExpressionVariables) {
        super("edit", "carrier edit <carrier> <new formula>", 2, "Edit a parcel carrier's price formula");
        this.vintageController = vintageController;
        this.priceExpressionVariables = priceExpressionVariables;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String carrierName = args[0];
        String formula = args[1];

        if (!vintageController.containsCarrierByName(carrierName)) {
            logger.warn("Unknown parcel carrier: " + carrierName);
            return;
        }

        ParcelCarrier carrier = vintageController.getCarrierByName(carrierName);

        if (!vintageController.isFormulaValid(formula, priceExpressionVariables)) {
            logger.warn("Invalid formula: " + formula);
            logger.warn("Please use the following variables: " + StringUtils.joinQuoted(priceExpressionVariables, ", "));
            return;
        }

        carrier.setExpeditionPriceExpression(formula);
        logger.info("'" + carrier.getName() + "' formula updated successfully.");
    }
}
