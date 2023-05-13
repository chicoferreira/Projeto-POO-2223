package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.condition.ItemConditionType;

import java.math.BigDecimal;
import java.util.Objects;
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

    public TshirtItem(TshirtItem item) {
        this(item.getOwnerUuid(),
                item.getAlphanumericId(),
                item.getCurrentStock(),
                item.getItemCondition(),
                item.getDescription(),
                item.getBrand(),
                item.getBasePrice(),
                item.getParcelCarrierName(),
                item.getSize(),
                item.getPattern());
    }

    public TshirtItem(UUID ownerUuid,
                      String alphanumericId,
                      int currentStock,
                      ItemCondition itemCondition,
                      String description,
                      String brand,
                      BigDecimal basePrice,
                      String parcelCarrierName,
                      TshirtItemSize size,
                      TshirtItemPattern pattern) {
        super(ownerUuid, alphanumericId, currentStock, itemCondition, description, brand, basePrice, parcelCarrierName);
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
    public <T> T getProperty(ItemProperty property, Class<T> expectedClass) {
        return switch (property) {
            case TSHIRT_SIZE -> expectedClass.cast(getSize());
            case TSHIRT_PATTERN -> expectedClass.cast(getPattern());
            default -> super.getProperty(property, expectedClass);
        };
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
    public Item clone() {
        return new TshirtItem(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TshirtItem that = (TshirtItem) o;
        return getSize() == that.getSize() && getPattern() == that.getPattern();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSize(), getPattern());
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
