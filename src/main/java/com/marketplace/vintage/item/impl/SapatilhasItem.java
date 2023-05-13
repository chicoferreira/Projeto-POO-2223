package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.condition.UsedItemCondition;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class SapatilhasItem extends Item {

    private final int size;
    private final boolean hasLaces;
    private final String color;
    private final int collectionYear;

    public SapatilhasItem(SapatilhasItem sapatilhasItem) {
        this(sapatilhasItem.getOwnerUuid(),
                sapatilhasItem.getAlphanumericId(),
                sapatilhasItem.getCurrentStock(),
                sapatilhasItem.getItemCondition(),
                sapatilhasItem.getDescription(),
                sapatilhasItem.getBrand(),
                sapatilhasItem.getBasePrice(),
                sapatilhasItem.getParcelCarrierName(),
                sapatilhasItem.getSize(),
                sapatilhasItem.hasLaces(),
                sapatilhasItem.getColor(),
                sapatilhasItem.getCollectionYear());
    }

    public SapatilhasItem(UUID ownerUuid,
                          String alphanumericId,
                          int currentStock,
                          ItemCondition itemCondition,
                          String description,
                          String brand,
                          BigDecimal basePrice,
                          String parcelCarrierName,
                          int size,
                          boolean hasLaces,
                          String color,
                          int collectionYear) {
        super(ownerUuid, alphanumericId, currentStock, itemCondition, description, brand, basePrice, parcelCarrierName);
        this.size = size;
        this.hasLaces = hasLaces;
        this.color = color;
        this.collectionYear = collectionYear;
    }

    public int getSize() {
        return size;
    }

    public boolean hasLaces() {
        return hasLaces;
    }

    public String getColor() {
        return color;
    }

    public int getCollectionYear() {
        return collectionYear;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SAPATILHAS;
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        ItemCondition itemCondition = getItemCondition();
        if (itemCondition instanceof UsedItemCondition usedItemCondition) {
            BigDecimal previousOwnersMultiplier = BigDecimal.valueOf(usedItemCondition.getNumberOfPreviousOwners() * 0.5);
            BigDecimal conditionMultiplier = BigDecimal.valueOf(usedItemCondition.getConditionLevel() * 0.1);
            return getBasePrice().negate()
                    .multiply(previousOwnersMultiplier)
                    .multiply(conditionMultiplier);
        }

        return BigDecimal.ZERO;
    }

    @Override
    public <T> T getProperty(ItemProperty property, Class<T> expectedClass) {
        return switch (property) {
            case SAPATILHA_SIZE -> expectedClass.cast(getSize());
            case HAS_LACES -> expectedClass.cast(hasLaces());
            case COLOR -> expectedClass.cast(getColor());
            case COLLECTION_YEAR -> expectedClass.cast(getCollectionYear());
            default -> super.getProperty(property, expectedClass);
        };
    }

    @Override
    public Item clone() {
        return new SapatilhasItem(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SapatilhasItem that = (SapatilhasItem) o;
        return getSize() == that.getSize() && hasLaces == that.hasLaces && getCollectionYear() == that.getCollectionYear() && Objects.equals(getColor(), that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSize(), hasLaces, getColor(), getCollectionYear());
    }

    @Override
    public String toString() {
        return "SapatilhasItem{" +
                "size=" + getSize() +
                ", hasLaces=" + hasLaces() +
                ", color='" + getColor() + '\'' +
                ", collectionYear=" + getCollectionYear() +
                ", alphanumericID='" + getAlphanumericId() + '\'' +
                ", itemCondition=" + getItemCondition() +
                ", description='" + getDescription() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", basePrice=" + getBasePrice() +
                ", parcelCarrierName=" + getParcelCarrierName() +
                '}';
    }
}
