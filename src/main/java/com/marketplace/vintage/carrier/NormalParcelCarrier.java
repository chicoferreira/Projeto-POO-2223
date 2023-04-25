package com.marketplace.vintage.carrier;

import com.marketplace.vintage.item.ItemType;

import java.util.UUID;

public class NormalParcelCarrier extends ParcelCarrier {

    public NormalParcelCarrier(UUID uuid, String name, String expeditionPriceExpression) {
        super(uuid, name, expeditionPriceExpression, ParcelCarrierType.NORMAL);
    }

    @Override
    public boolean canDeliverItemType(ItemType itemType) {
        return !itemType.isPremium();
    }
}
