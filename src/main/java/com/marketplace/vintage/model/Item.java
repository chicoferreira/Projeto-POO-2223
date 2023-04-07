package com.marketplace.vintage.model;

import com.marketplace.vintage.model.condition.ItemCondition;
import com.marketplace.vintage.model.Transporter;

import java.math.BigDecimal;

public abstract class Item {

    private final ItemCondition itemCondition;
    private final String description;
    private final String brand;
    private final String alphanumericCode;
    private final BigDecimal basePrice;
    private final int transporterID;

    public Item(ItemCondition itemCondition, String description, String brand, String alphanumericCode, BigDecimal basePrice, int transporterID) {
        this.itemCondition = itemCondition;
        this.description = description;
        this.brand = brand;
        this.alphanumericCode = alphanumericCode;
        this.basePrice = basePrice;
        this.transporterID = transporterID;
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

    public int getTransporterID() { return transporterID; }

    /**
     * @return the final price of the item, after applying the price correction
     */
    public BigDecimal getFinalPrice(int currentYear) {
        return (basePrice.add(getPriceCorrection(currentYear)));
    }

    /**
     * @return the price correction to be applied to the base price
     */
    public abstract BigDecimal getPriceCorrection(int currentYear);

}
