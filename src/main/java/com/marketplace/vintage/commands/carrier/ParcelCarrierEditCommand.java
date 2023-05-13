package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

import java.util.List;

public class ParcelCarrierEditCommand extends BaseCommand {

    private final Vintage vintage;
    private final List<String> priceExpressionVariables;

    public ParcelCarrierEditCommand(Vintage vintage, List<String> priceExpressionVariables) {
        super("edit", "carrier edit <carrier>", 1, "Edit a parcel carrier's price formula");
        this.vintage = vintage;
        this.priceExpressionVariables = priceExpressionVariables;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String carrierName = args[0];

        if (!vintage.containsCarrierByName(carrierName)) {
            logger.warn("Unknown parcel carrier: " + carrierName);
            return;
        }

        logger.info("Please enter the expression using the following variables: ");
        logger.info(StringUtils.joinQuoted(priceExpressionVariables, ", "));

        String formula = inputPrompter.askForInput(logger, "Expression >",
                InputMapper.ofExpression(vintage::isFormulaValid, priceExpressionVariables));

        ParcelCarrier carrier = vintage.getCarrierByName(carrierName);

        if (!vintage.isFormulaValid(formula, priceExpressionVariables)) {
            logger.warn("Invalid formula: " + formula);
            logger.warn("Please use the following variables: " + StringUtils.joinQuoted(priceExpressionVariables, ", "));
            return;
        }

        vintage.setCarrierExpeditionPriceExpression(carrier, formula);
        logger.info("'" + carrier.getName() + "' formula updated successfully.");
    }
}
