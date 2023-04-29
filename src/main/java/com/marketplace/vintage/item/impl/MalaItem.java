package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.UUID;

public class MalaItem extends Item {

    private final int dimensionArea;
    private final String material;
    private final int collectionYear;
    private final int appreciationRateOverYears;

    public MalaItem(UUID ownerUuid,
                    String alphanumericId,
                    ItemCondition itemCondition,
                    String description,
                    String brand,
                    BigDecimal basePrice,
                    UUID parcelCarrierUuid,
                    int dimensionArea,
                    String material,
                    int collectionYear,
                    int appreciationRateOverYears) {
        super(ownerUuid, alphanumericId, itemCondition, description, brand, basePrice, parcelCarrierUuid);
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
    public ItemType getItemType() {
        return ItemType.MALA;
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        if (currentYear < collectionYear) {
            throw new IllegalArgumentException("Current year cannot be before collection year");
        }

        BigDecimal result = this.getBasePrice();

        int yearsSinceCollection = currentYear - collectionYear;
        if (yearsSinceCollection == 0) {
            return BigDecimal.valueOf(0);
        }

        for (int i = 0; i < yearsSinceCollection; i++) {
            result = result.multiply(BigDecimal.valueOf(1 - appreciationRateOverYears / 100.0));
        }

        return this.getBasePrice().subtract(result).negate();
    }

    @Override
    public String toString() {
        return "MalaItem{" +
               "dimensionArea=" + getDimensionArea() +
               ", material='" + getMaterial() + '\'' +
               ", collectionYear=" + getCollectionYear() +
               ", appreciationRateOverYears=" + getAppreciationRateOverYears() +
               ", alphanumericID='" + getAlphanumericId() + '\'' +
               ", itemCondition=" + getItemCondition() +
               ", description='" + getDescription() + '\'' +
               ", brand='" + getBrand() + '\'' +
               ", basePrice=" + getBasePrice() +
               ", parcelCarrierUuid=" + getParcelCarrierUuid() +
               '}';
    }
}
