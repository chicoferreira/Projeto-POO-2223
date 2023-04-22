package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class PremiumMalaItem extends MalaItem {

    public PremiumMalaItem(ItemCondition itemCondition,
                           String description,
                           String brand,
                           String alphanumericCode,
                           BigDecimal basePrice,
                           UUID parcelCarrierUuid,
                           int dimensionArea,
                           String material,
                           int collectionYear,
                           int appreciationRateOverYears) {
        super(itemCondition, description, brand, alphanumericCode, basePrice, parcelCarrierUuid, dimensionArea, material, collectionYear, appreciationRateOverYears);
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        return super.getPriceCorrection(currentYear).negate();
    }
}
