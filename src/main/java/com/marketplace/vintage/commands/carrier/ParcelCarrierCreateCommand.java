package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierFactory;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

import java.util.List;

public class ParcelCarrierCreateCommand extends BaseCommand {

    private final VintageController vintageController;
    private final String defaultExpression;
    private final List<String> expressionVariables;

    public ParcelCarrierCreateCommand(VintageController vintageController, String defaultExpression, List<String> expressionVariables) {
        super("create", "carrier create <carrier name> (premium)", 1, "Create a new parcel carrier");
        this.vintageController = vintageController;
        this.defaultExpression = defaultExpression;
        this.expressionVariables = expressionVariables;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String parcelCarrierName = args[0];

        boolean premium = args.length > 1 && args[1].equalsIgnoreCase("premium");

        ParcelCarrier parcelCarrier = premium ?
                ParcelCarrierFactory.createPremiumParcelCarrier(parcelCarrierName) :
                ParcelCarrierFactory.createNormalParcelCarrier(parcelCarrierName);

        logger.info("Do you want to set a custom expedition price expression? (y/n)");
        logger.info("The default one is: " + defaultExpression);
        boolean response = getInputPrompter().askForInput(logger, "Boolean >", InputMapper.BOOLEAN);

        if (response) {
            logger.info("Please enter the expression using the following variables: ");
            logger.info(StringUtils.joinQuoted(expressionVariables, ", "));

            String expression = getInputPrompter().askForInput(logger, "Expression >",
                    InputMapper.ofExpression(vintageController::isFormulaValid, expressionVariables));
            parcelCarrier.setExpeditionPriceExpression(expression);
        }

        try {
            vintageController.registerParcelCarrier(parcelCarrier);
        } catch (Exception e) {
            logger.warn("Failed to create parcel carrier: " + e.getMessage());
            return;
        }

        if (premium) {
            logger.info("Premium parcel carrier " + parcelCarrier.getName() + " created successfully");
        } else {
            logger.info("Parcel carrier " + parcelCarrier.getName() + " created successfully");
        }
    }

}
