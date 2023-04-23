package com.marketplace.vintage.item;

import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.utils.AlphanumericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Item {

    private final String alphanumericCode;
    private final ItemCondition itemCondition;
    private final String description;
    private final String brand;
    private final BigDecimal basePrice;
    private final UUID parcelCarrierUuid;

    public Item(String alphanumericCode, ItemCondition itemCondition, String description, String brand,  BigDecimal basePrice, UUID parcelCarrierUuid) {
        this.alphanumericCode = alphanumericCode;
        this.itemCondition = itemCondition;
        this.description = description;
        this.brand = brand;
        this.basePrice = basePrice;
        this.parcelCarrierUuid = parcelCarrierUuid;
    }

    public String getAlphanumericCode() {
        return alphanumericCode;
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
