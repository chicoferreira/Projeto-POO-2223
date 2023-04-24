package com.marketplace.vintage.carrier;

import com.marketplace.vintage.VintageApplication;
import com.marketplace.vintage.expression.Expression;
import com.marketplace.vintage.expression.ExpressionBuilder;

import java.util.UUID;

public class ParcelCarrierFactory {

    private final static Expression DEFAULT_EXPEDITION_PRICE_EXPRESSION = ExpressionBuilder.newBuilder()
            .addVariable("basePrice")
            .addVariable("tax")
            .build(VintageApplication.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING);

    public static ParcelCarrier createNormalParcelCarrier(String name) {
        return new NormalParcelCarrier(UUID.randomUUID(), name, DEFAULT_EXPEDITION_PRICE_EXPRESSION);
    }

    public static ParcelCarrier createPremiumParcelCarrier(String name) {
        return new PremiumParcelCarrier(UUID.randomUUID(), name, DEFAULT_EXPEDITION_PRICE_EXPRESSION);
    }

}
