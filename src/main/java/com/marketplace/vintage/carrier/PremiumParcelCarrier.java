package com.marketplace.vintage.carrier;

import com.marketplace.vintage.item.ItemType;

import java.util.UUID;

public class PremiumParcelCarrier extends ParcelCarrier {

    public PremiumParcelCarrier(UUID uuid, String name, String expeditionPriceExpression) {
        super(uuid, name, expeditionPriceExpression, ParcelCarrierType.PREMIUM);
    }

    @Override
    public boolean canDeliverItemType(ItemType itemType) {
        return itemType.isPremium();
    }
}
