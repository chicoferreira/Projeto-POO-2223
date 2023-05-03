package com.marketplace.vintage.carrier;

import com.marketplace.vintage.VintageConstants;

public class ParcelCarrierFactory {

    public static ParcelCarrier createNormalParcelCarrier(String name) {
        return new NormalParcelCarrier(name, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING);
    }

    public static ParcelCarrier createPremiumParcelCarrier(String name) {
        return new PremiumParcelCarrier(name, VintageConstants.DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING);
    }

}
