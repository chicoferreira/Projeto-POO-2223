package com.marketplace.vintage.carrier;

import com.marketplace.vintage.item.ItemType;

public class NormalParcelCarrier extends ParcelCarrier {

    public NormalParcelCarrier(String name, String expeditionPriceExpression) {
        super(name, expeditionPriceExpression, ParcelCarrierType.NORMAL);
    }

    @Override
    public boolean canDeliverItemType(ItemType itemType) {
        return !itemType.isPremium();
    }
}
