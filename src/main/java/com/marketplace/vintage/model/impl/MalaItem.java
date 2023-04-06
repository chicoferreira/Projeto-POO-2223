package com.marketplace.vintage.model.impl;

import com.marketplace.vintage.model.Item;
import com.marketplace.vintage.model.condition.ItemCondition;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MalaItem extends Item {

    private final int dimensionArea;
    private final String material;
    private final int collectionYear;
    private final int appreciationRateOverYears;

    public MalaItem(ItemCondition itemCondition, String description, String brand, String alphanumericCode, BigDecimal basePrice, int dimensionArea, String material, int collectionYear, int appreciationRateOverYears) {
        super(itemCondition, description, brand, alphanumericCode, basePrice);
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

        // TODO: esclarecer enunciado da correção do preço aqui

        return BigDecimal.ZERO;
    }
}