package com.marketplace.vintage.carrier;

import com.marketplace.vintage.item.ItemType;

public class PremiumParcelCarrier extends ParcelCarrier {

    public PremiumParcelCarrier(PremiumParcelCarrier premiumParcelCarrier) {
        super(premiumParcelCarrier);
    }

    public PremiumParcelCarrier(String name, String expeditionPriceExpression) {
        super(name, expeditionPriceExpression, ParcelCarrierType.PREMIUM);
    }

    @Override
    public boolean canDeliverItemType(ItemType itemType) {
        return itemType.isPremium();
    }

    @Override
    public ParcelCarrier clone() {
        return new PremiumParcelCarrier(this);
    }
}
