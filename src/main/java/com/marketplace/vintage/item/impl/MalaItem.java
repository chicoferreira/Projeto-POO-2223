package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.UUID;

public class MalaItem extends Item {

    private final int dimensionArea;
    private final String material;
    private final int collectionYear;
    private final int appreciationRateOverYears;

    public MalaItem(ItemCondition itemCondition,
                    String description,
                    String brand,
                    String alphanumericCode,
                    BigDecimal basePrice,
                    UUID parcelCarrierUuid,
                    int dimensionArea,
                    String material,
                    int collectionYear,
                    int appreciationRateOverYears) {
        super(itemCondition, description, brand, alphanumericCode, basePrice, parcelCarrierUuid);
        this.dimensionArea = dimensionArea;
        this.material = material;
        this.collectionYear = collectionYear;
        this.appreciationRateOverYears = appreciationRateOverYears;
    }

    public int getDimensionArea() {
        return dimensionArea;
    }

    public String getMaterial() {
        return material;
    }

    public int getCollectionYear() {
        return collectionYear;
    }

    /**
     * @return the appreciation rate over years in percentage (e.g. 10 means 10%)
     */
    public int getAppreciationRateOverYears() {
        return appreciationRateOverYears;
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        if (currentYear < collectionYear) {
            throw new IllegalArgumentException("Current year cannot be before collection year");
        }

        BigDecimal result = this.getBasePrice();

        int yearsSinceCollection = currentYear - collectionYear;
        for (int i = 0; i < yearsSinceCollection; i++) {
            result = result.multiply(BigDecimal.valueOf((appreciationRateOverYears + 100) / 100.0));
        }

        return result;
    }
}
