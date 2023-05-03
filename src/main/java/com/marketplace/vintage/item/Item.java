package com.marketplace.vintage.item;

import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public abstract class Item {

    private final UUID ownerUuid;
    private final String alphanumericId;
    private final ItemCondition itemCondition;
    private final String description;
    private final String brand;
    private final BigDecimal basePrice;
    private final String parcelCarrierName;

    public Item(UUID ownerUuid, String alphanumericCode, ItemCondition itemCondition, String description, String brand, BigDecimal basePrice, String parcelCarrierName) {
        this.ownerUuid = ownerUuid;
        this.alphanumericId = alphanumericCode;
        this.itemCondition = itemCondition;
        this.description = description;
        this.brand = brand;
        this.basePrice = basePrice;
        this.parcelCarrierName = parcelCarrierName;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public String getAlphanumericId() {
        return alphanumericId;
    }

    public ItemCondition getItemCondition() {
        return itemCondition;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public String getParcelCarrierName() {
        return parcelCarrierName;
    }

    public abstract ItemType getItemType();

    /**
     * @return the final price of the item, after applying the price correction
     */
    public BigDecimal getFinalPrice(int currentYear) {
        return (basePrice.add(getPriceCorrection(currentYear))).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @return the price correction to be applied to the base price
     */
    public abstract BigDecimal getPriceCorrection(int currentYear);

}
