package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class PremiumSapatilhasItem extends SapatilhasItem {

    private final int appreciationRateOverYears;

    public PremiumSapatilhasItem(ItemCondition itemCondition,
                                 String description,
                                 String brand,
                                 String alphanumericCode,
                                 BigDecimal basePrice,
                                 UUID parcelCarrierUuid,
                                 int size,
                                 boolean hasLaces,
                                 String color,
                                 int collectionYear,
                                 int appreciationRateOverYears) {
        super(itemCondition, description, brand, alphanumericCode, basePrice, parcelCarrierUuid, size, hasLaces, color, collectionYear);
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

}
