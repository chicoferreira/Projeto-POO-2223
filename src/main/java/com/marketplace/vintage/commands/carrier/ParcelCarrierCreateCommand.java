package com.marketplace.vintage.commands.carrier;

import com.marketplace.vintage.VintageApplication;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierFactory;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.expression.Expression;
import com.marketplace.vintage.expression.ExpressionBuilder;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
            Expression expression = askForExpression(logger);
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

    public Expression askForExpression(Logger logger) {
        List<String> allowedVariables = VintageApplication.EXPEDITION_PRICE_EXPRESSION_VARIABLES;
        StringBuilder message = new StringBuilder("Please enter the expression using the following variables: ");
        for (String variable : allowedVariables) {
            message.append(variable).append(" ");
        }
        message.append("\n");
        String expressionString = getInputPrompter().askForInput(logger, message.toString(), InputMapper.STRING);

        // Check for unknown variables
        Predicate<String> variableValidator = variable -> allowedVariables.contains(variable) || StringUtils.isNumeric(variable) || StringUtils.isOperator(variable);
        if (!StringUtils.containsOnlyAllowedTokens(expressionString, variableValidator)) {
            logger.warn("Invalid expression, please try again");
            return askForExpression(logger);
        }

        List<String> variables = new ArrayList<>();
        for (String variable : allowedVariables) {
            if (expressionString.contains(variable)) {
                variables.add(variable);
            }
        }

        return ExpressionBuilder.newBuilder()
                .withVariables(variables)
                .build(expressionString);
    }
}
