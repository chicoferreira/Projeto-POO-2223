package com.marketplace.vintage.carrier;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.expression.Expression;
import com.marketplace.vintage.expression.ExpressionFactory;

import java.util.UUID;

public class ParcelCarrierFactory {

    private final static Expression DEFAULT_EXPEDITION_PRICE_EXPRESSION = ExpressionFactory.createExpression(VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES);

    public static ParcelCarrier createNormalParcelCarrier(String name) {
        return new NormalParcelCarrier(UUID.randomUUID(), name, DEFAULT_EXPEDITION_PRICE_EXPRESSION);
    }

    public static ParcelCarrier createPremiumParcelCarrier(String name) {
        return new PremiumParcelCarrier(UUID.randomUUID(), name, DEFAULT_EXPEDITION_PRICE_EXPRESSION);
    }

}
