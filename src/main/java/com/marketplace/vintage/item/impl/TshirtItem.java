package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.condition.ItemConditionType;

import java.math.BigDecimal;
import java.util.UUID;

public class TshirtItem extends Item {

    public enum TshirtItemSize {
        SMALL, MEDIUM, LARGE, EXTRA_LARGE
    }

    public enum TshirtItemPattern {
        PLAIN, STRIPES, PALM_TREES
    }

    private final TshirtItemSize size;
    private final TshirtItemPattern pattern;

    public TshirtItem(String alphanumericID,
                      ItemCondition itemCondition,
                      String description,
                      String brand,
                      BigDecimal basePrice,
                      UUID parcelCarrierUuid,
                      TshirtItemSize size,
                      TshirtItemPattern pattern) {
        super(alphanumericID, itemCondition, description, brand, basePrice, parcelCarrierUuid);
        this.size = size;
        this.pattern = pattern;
    }

    public TshirtItemSize getSize() {
        return size;
    }

    public TshirtItemPattern getPattern() {
        return pattern;
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        if (getItemCondition().getType() == ItemConditionType.USED && getPattern() != TshirtItemPattern.PLAIN) {
            return getBasePrice().negate().multiply(BigDecimal.valueOf(0.5));
        }

        return BigDecimal.ZERO;
    }

}
