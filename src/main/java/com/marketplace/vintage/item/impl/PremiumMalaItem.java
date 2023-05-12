package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.UUID;

public class PremiumMalaItem extends MalaItem {

    public PremiumMalaItem(UUID ownerUuid,
                           String alphanumericId,
                           ItemCondition itemCondition,
                           String description,
                           String brand,
                           BigDecimal basePrice,
                           String parcelCarrierName,
                           int dimensionArea,
                           String material,
                           int collectionYear,
                           int appreciationRateOverYears) {
        super(ownerUuid, alphanumericId, itemCondition, description, brand, basePrice, parcelCarrierName, dimensionArea, material, collectionYear, appreciationRateOverYears);
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        return super.getPriceCorrection(currentYear).negate();
    }

    @Override
    public ItemType getItemType() {
        return ItemType.MALA_PREMIUM;
    }

    @Override
    public <T> T getProperty(ItemProperty property, Class<T> expectedClass) {
        return super.getProperty(property, expectedClass);
    }

    @Override
    public String toString() {
        return "PremiumMalaItem{" +
               "alphanumericID='" + getAlphanumericId() + '\'' +
               ", itemCondition=" + getItemCondition() +
               ", description='" + getDescription() + '\'' +
               ", brand='" + getBrand() + '\'' +
               ", basePrice=" + getBasePrice() +
               ", parcelCarrierName=" + getParcelCarrierName() +
               ", dimensionArea=" + getDimensionArea() +
               ", material='" + getMaterial() + '\'' +
               ", collectionYear=" + getCollectionYear() +
               ", appreciationRateOverYears=" + getAppreciationRateOverYears() +
               '}';
    }
}
