package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierFactory;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.expression.Expression;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

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

        logger.info("Do you want to set a custom price expression? (y/n)");
        logger.info("The default one is: " + VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING);
        boolean response = getInputPrompter().askForInput(logger,">", InputMapper.BOOLEAN);

        if (response) {
            logger.info("Please enter the expression using the following variables: ");
            logger.info(StringUtils.joinQuoted(VintageConstants.EXPEDITION_PRICE_EXPRESSION_VARIABLES, ", "));

            Expression expression = getInputPrompter().askForInput(logger, ">", InputMapper.ofExpression(VintageConstants.EXPEDITION_PRICE_EXPRESSION_VARIABLES));

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
