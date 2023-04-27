package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.UUID;

public class PremiumMalaItem extends MalaItem {

    public PremiumMalaItem(String alphanumericId,
                           ItemCondition itemCondition,
                           String description,
                           String brand,
                           BigDecimal basePrice,
                           UUID parcelCarrierUuid,
                           int dimensionArea,
                           String material,
                           int collectionYear,
                           int appreciationRateOverYears) {
        super(alphanumericId, itemCondition, description, brand, basePrice, parcelCarrierUuid, dimensionArea, material, collectionYear, appreciationRateOverYears);
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        return super.getPriceCorrection(currentYear).negate();
    }

    @Override
    public String toString() {
        return "PremiumMalaItem{" +
               "alphanumericID='" + getAlphanumericID() + '\'' +
               ", itemCondition=" + getItemCondition() +
               ", description='" + getDescription() + '\'' +
               ", brand='" + getBrand() + '\'' +
               ", basePrice=" + getBasePrice() +
               ", parcelCarrierUuid=" + getParcelCarrierUuid() +
               ", dimensionArea=" + getDimensionArea() +
               ", material='" + getMaterial() + '\'' +
               ", collectionYear=" + getCollectionYear() +
               ", appreciationRateOverYears=" + getAppreciationRateOverYears() +
               '}';
    }
}
