package com.marketplace.vintage.carrier;

import java.util.UUID;

public class ParcelCarrierFactory {

    public static ParcelCarrier createNormalParcelCarrier(String name) {
        return new NormalParcelCarrier(UUID.randomUUID(), name);
    }

    public static ParcelCarrier createPremiumParcelCarrier(String name) {
        return new PremiumParcelCarrier(UUID.randomUUID(), name);
    }

}
