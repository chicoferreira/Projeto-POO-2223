package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

public class ParcelCarrierEditCommand extends BaseCommand {

    private final ParcelCarrierManager parcelCarrierManager;
    private final ExpressionSolver expressionSolver;

    public ParcelCarrierEditCommand(ParcelCarrierManager parcelCarrierManager, ExpressionSolver expressionSolver) {
        super("edit", "carrier edit <carrier> <new formula>", 2, "Edit a parcel carrier's price formula");
        this.parcelCarrierManager = parcelCarrierManager;
        this.expressionSolver = expressionSolver;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String carrierName = args[0];
        String formula = args[1];

        if (!parcelCarrierManager.containsCarrierByName(carrierName)) {
            logger.warn("Unknown parcel carrier: " + carrierName);
            return;
        }

        ParcelCarrier carrier = parcelCarrierManager.getCarrierByName(carrierName);

        if (!expressionSolver.isValid(formula, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES)) {
            logger.warn("Invalid formula: " + formula);
            logger.warn("Please use the following variables: " + StringUtils.joinQuoted(VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES, ", "));
            return;
        }

        carrier.setExpeditionPriceExpression(formula);
        logger.info("'" + carrier.getName() + "' formula updated successfully.");
    }
}
