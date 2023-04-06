package com.marketplace.vintage.model;

import com.marketplace.vintage.model.condition.ItemCondition;

import java.math.BigDecimal;

public abstract class Item {

    private final ItemCondition itemCondition;
    private final String description;
    private final String brand;
    private final String alphanumericCode;
    private final BigDecimal basePrice;

    public Item(ItemCondition itemCondition, String description, String brand, String alphanumericCode, BigDecimal basePrice) {
        this.itemCondition = itemCondition;
        this.description = description;
        this.brand = brand;
        this.alphanumericCode = alphanumericCode;
        this.basePrice = basePrice;
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