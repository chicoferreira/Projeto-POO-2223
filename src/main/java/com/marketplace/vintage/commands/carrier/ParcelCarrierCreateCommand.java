package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.VintageApplication;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierFactory;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.expression.Expression;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.logging.Logger;

public class ParcelCarrierCreateCommand extends BaseCommand {

    private final ParcelCarrierManager parcelCarrierManager;

    public ParcelCarrierCreateCommand(ParcelCarrierManager parcelCarrierManager) {
        super("create", "carrier create <carrier name>", 1, "Create a new parcel carrier");
        this.parcelCarrierManager = parcelCarrierManager;
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        String parcelCarrierName = args[0];

        ParcelCarrier parcelCarrier = ParcelCarrierFactory.createNormalParcelCarrier(parcelCarrierName);

        String message = "Do you want to set a custom price expression? The default one is: " + VintageApplication.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING + " (y/n)";
        boolean response = getInputPrompter().askForInput(logger, message, InputMapper.BOOLEAN);

        if (response) {
            StringBuilder expressionMessage = new StringBuilder("Please enter the expression using the following variables: ");
            for (String variable : VintageApplication.EXPEDITION_PRICE_EXPRESSION_VARIABLES) {
                expressionMessage.append(variable).append(" ");
            }
            expressionMessage.append("\n");
            Expression expression = getInputPrompter().askForInput(logger, expressionMessage.toString(), InputMapper.ofExpression(VintageApplication.EXPEDITION_PRICE_EXPRESSION_VARIABLES));

            parcelCarrier.setExpeditionPriceExpression(expression);
        }

        try {
            parcelCarrierManager.registerParcelCarrier(parcelCarrier);
        } catch (Exception e) {
            logger.warn("Failed to create parcel carrier: " + e.getMessage());
            return;
        }

        logger.info("Parcel carrier " + parcelCarrier.getName() + " created successfully");
    }

}
