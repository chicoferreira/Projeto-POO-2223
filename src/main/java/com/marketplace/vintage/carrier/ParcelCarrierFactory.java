package com.marketplace.vintage.carrier;

import com.marketplace.vintage.VintageConstants;

import java.util.UUID;

public class ParcelCarrierFactory {

    public static ParcelCarrier createNormalParcelCarrier(String name) {
        return new NormalParcelCarrier(UUID.randomUUID(), name, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING);
    }

    public static ParcelCarrier createPremiumParcelCarrier(String name) {
        return new PremiumParcelCarrier(UUID.randomUUID(), name, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING);
    }

}
