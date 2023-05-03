package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemType;
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

    public TshirtItem(UUID ownerUuid,
                      String alphanumericId,
                      ItemCondition itemCondition,
                      String description,
                      String brand,
                      BigDecimal basePrice,
                      String parcelCarrierName,
                      TshirtItemSize size,
                      TshirtItemPattern pattern) {
        super(ownerUuid, alphanumericId, itemCondition, description, brand, basePrice, parcelCarrierName);
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
    public ItemType getItemType() {
        return ItemType.TSHIRT;
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        if (getItemCondition().getType() == ItemConditionType.USED && getPattern() != TshirtItemPattern.PLAIN) {
            return getBasePrice().negate().multiply(BigDecimal.valueOf(0.5));
        }

        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "TshirtItem{" +
               "size=" + getSize() +
               ", pattern=" + getPattern() +
               ", alphanumericID='" + getAlphanumericId() + '\'' +
               ", itemCondition=" + getItemCondition() +
               ", description='" + getDescription() + '\'' +
               ", brand='" + getBrand() + '\'' +
               ", basePrice=" + getBasePrice() +
               ", parcelCarrierName=" + getParcelCarrierName() +
               '}';
    }
}
