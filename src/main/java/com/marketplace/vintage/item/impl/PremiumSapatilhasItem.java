package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.UUID;

public class PremiumSapatilhasItem extends SapatilhasItem {

    private final int appreciationRateOverYears;

    public PremiumSapatilhasItem(UUID ownerUuid, String alphanumericId,
                                 ItemCondition itemCondition,
                                 String description,
                                 String brand,
                                 BigDecimal basePrice,
                                 String parcelCarrierName,
                                 int size,
                                 boolean hasLaces,
                                 String color,
                                 int collectionYear,
                                 int appreciationRateOverYears) {
        super(ownerUuid, alphanumericId, itemCondition, description, brand, basePrice, parcelCarrierName, size, hasLaces, color, collectionYear);
        this.appreciationRateOverYears = appreciationRateOverYears;
    }

    /**
     * @return the appreciation rate over years in percentage (e.g. 10 means 10%)
     */
    public int getAppreciationRateOverYears() {
        return appreciationRateOverYears;
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        if (currentYear < getCollectionYear()) {
            throw new IllegalArgumentException("Current year cannot be before collection year");
        }

        BigDecimal result = this.getBasePrice();

        int yearsSinceCollection = currentYear - getCollectionYear();
        if (yearsSinceCollection == 0) {
            return BigDecimal.valueOf(0);
        }

        for (int i = 0; i < yearsSinceCollection; i++) {
            result = result.multiply(BigDecimal.valueOf(1 - appreciationRateOverYears / 100.0));
        }

        return this.getBasePrice().subtract(result);
    }

    @Override
    public <T> T getProperty(ItemProperty property, Class<T> expectedClass) {
        return switch (property) {
            case APPRECIATION_RATE_OVER_YEARS -> expectedClass.cast(getAppreciationRateOverYears());
            default -> super.getProperty(property, expectedClass);
        };
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SAPATILHAS_PREMIUM;
    }

    @Override
    public String toString() {
        return "PremiumSapatilhasItem{" +
               "appreciationRateOverYears=" + getAppreciationRateOverYears() +
               ", alphanumericID='" + getAlphanumericId() + '\'' +
               ", itemCondition=" + getItemCondition() +
               ", description='" + getDescription() + '\'' +
               ", brand='" + getBrand() + '\'' +
               ", basePrice=" + getBasePrice() +
               ", parcelCarrierName=" + getParcelCarrierName() +
               ", size=" + getSize() +
               ", hasLaces=" + hasLaces() +
               ", color='" + getColor() + '\'' +
               ", collectionYear=" + getCollectionYear() +
               '}';
    }
}
