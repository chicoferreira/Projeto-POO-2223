package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierFactory;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

import java.util.List;

public class ParcelCarrierCreateCommand extends BaseCommand {

    private final Vintage vintage;
    private final String defaultExpression;
    private final List<String> expressionVariables;

    public ParcelCarrierCreateCommand(Vintage vintage, String defaultExpression, List<String> expressionVariables) {
        super("create", "carrier create <carrier name> (premium)", 1, "Create a new parcel carrier");
        this.vintage = vintage;
        this.defaultExpression = defaultExpression;
        this.expressionVariables = expressionVariables;
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String parcelCarrierName = args[0];

        boolean premium = args.length > 1 && args[1].equalsIgnoreCase("premium");

        ParcelCarrier parcelCarrier = premium ?
                ParcelCarrierFactory.createPremiumParcelCarrier(parcelCarrierName) :
                ParcelCarrierFactory.createNormalParcelCarrier(parcelCarrierName);

        logger.info("Do you want to set a custom expedition price expression? (y/n)");
        logger.info("The default one is: " + defaultExpression);
        boolean response = inputPrompter.askForInput(logger, "Boolean >", InputMapper.BOOLEAN);

        if (response) {
            logger.info("Please enter the expression using the following variables: ");
            logger.info(StringUtils.joinQuoted(expressionVariables, ", "));

            String expression = inputPrompter.askForInput(logger, "Expression >",
                    InputMapper.ofExpression(vintage::isFormulaValid, expressionVariables));
            parcelCarrier.setExpeditionPriceExpression(expression);
        }

        try {
            vintage.registerParcelCarrier(parcelCarrier);
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
