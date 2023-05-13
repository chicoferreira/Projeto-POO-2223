package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.UUID;

public class PremiumMalaItem extends MalaItem {

    public PremiumMalaItem(PremiumMalaItem item) {
        this(item.getOwnerUuid(),
                item.getAlphanumericId(),
                item.getCurrentStock(),
                item.getItemCondition(),
                item.getDescription(),
                item.getBrand(),
                item.getBasePrice(),
                item.getParcelCarrierName(),
                item.getDimensionArea(),
                item.getMaterial(),
                item.getCollectionYear(),
                item.getAppreciationRateOverYears());
    }

    public PremiumMalaItem(UUID ownerUuid,
                           String alphanumericId,
                           int currentStock,
                           ItemCondition itemCondition,
                           String description,
                           String brand,
                           BigDecimal basePrice,
                           String parcelCarrierName,
                           int dimensionArea,
                           String material,
                           int collectionYear,
                           int appreciationRateOverYears) {
        super(ownerUuid, alphanumericId, currentStock, itemCondition, description, brand, basePrice, parcelCarrierName, dimensionArea, material, collectionYear, appreciationRateOverYears);
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        return super.getPriceCorrection(currentYear).negate();
    }

    public int getAppreciationRateOverYears() {
        return super.getDepreciationRateOverYears();
    }

    @Override
    public ItemType getItemType() {
        return ItemType.MALA_PREMIUM;
    }

    @Override
    public <T> T getProperty(ItemProperty property, Class<T> expectedClass) {
        return switch (property) {
            case APPRECIATION_RATE_OVER_YEARS -> expectedClass.cast(getAppreciationRateOverYears());
            default -> super.getProperty(property, expectedClass);
        };
    }

    @Override
    public Item clone() {
        return new PremiumMalaItem(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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
