package com.marketplace.vintage.item;

import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Item {

    private final UUID uuid;
    private final ItemCondition itemCondition;
    private final String description;
    private final String brand;
    private final String alphanumericCode;
    private final BigDecimal basePrice;
    private final UUID parcelCarrierUuid;

    public Item(ItemCondition itemCondition, String description, String brand, String alphanumericCode, BigDecimal basePrice, UUID parcelCarrierUuid) {
        this(UUID.randomUUID(), itemCondition, description, brand, alphanumericCode, basePrice, parcelCarrierUuid);
    }

    public Item(UUID uuid, ItemCondition itemCondition, String description, String brand, String alphanumericCode, BigDecimal basePrice, UUID parcelCarrierUuid) {
        this.uuid = uuid;
        this.itemCondition = itemCondition;
        this.description = description;
        this.brand = brand;
        this.alphanumericCode = alphanumericCode;
        this.basePrice = basePrice;
        this.parcelCarrierUuid = parcelCarrierUuid;
    }

    public UUID getItemUuid() {
        return uuid;
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

    public String getAlphanumericCode() {
        return alphanumericCode;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public UUID getParcelCarrierUuid() {
        return parcelCarrierUuid;
    }

    /**
     * @return the final price of the item, after applying the price correction
     */
    public BigDecimal getFinalPrice(int currentYear) {
        return basePrice.add(getPriceCorrection(currentYear));
    }

    /**
     * @return the price correction to be applied to the base price
     */
    public abstract BigDecimal getPriceCorrection(int currentYear);

}
