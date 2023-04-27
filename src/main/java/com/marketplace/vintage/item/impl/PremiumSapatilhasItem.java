package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.UUID;

public class PremiumSapatilhasItem extends SapatilhasItem {

    private final int appreciationRateOverYears;

    public PremiumSapatilhasItem(String alphanumericId,
                                 ItemCondition itemCondition,
                                 String description,
                                 String brand,
                                 BigDecimal basePrice,
                                 UUID parcelCarrierUuid,
                                 int size,
                                 boolean hasLaces,
                                 String color,
                                 int collectionYear,
                                 int appreciationRateOverYears) {
        super(alphanumericId, itemCondition, description, brand, basePrice, parcelCarrierUuid, size, hasLaces, color, collectionYear);
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
    public String toString() {
        return "PremiumSapatilhasItem{" +
               "appreciationRateOverYears=" + getAppreciationRateOverYears() +
               ", alphanumericID='" + getAlphanumericID() + '\'' +
               ", itemCondition=" + getItemCondition() +
               ", description='" + getDescription() + '\'' +
               ", brand='" + getBrand() + '\'' +
               ", basePrice=" + getBasePrice() +
               ", parcelCarrierUuid=" + getParcelCarrierUuid() +
               ", size=" + getSize() +
               ", hasLaces=" + hasLaces() +
               ", color='" + getColor() + '\'' +
               ", collectionYear=" + getCollectionYear() +
               '}';
    }
}
