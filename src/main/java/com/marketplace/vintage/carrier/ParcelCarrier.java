package com.marketplace.vintage.carrier;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.item.ItemType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public abstract class ParcelCarrier implements Serializable {

    private final String name;
    private String expeditionPriceExpression;
    private final ParcelCarrierType type;

    public ParcelCarrier(String name, String expeditionPriceExpression, ParcelCarrierType type) {
        this.name = name;
        this.expeditionPriceExpression = expeditionPriceExpression;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ParcelCarrierType getType() {
        return type;
    }

    public String getExpeditionPriceExpression() {
        return expeditionPriceExpression;
    }

    /**
     * Set the price expression for this carrier, assuming the expression is valid
     */
    public void setExpeditionPriceExpression(String expeditionPriceExpression) {
        this.expeditionPriceExpression = expeditionPriceExpression;
    }

    public abstract boolean canDeliverItemType(ItemType itemType);

    public BigDecimal getBaseValueForExpedition(int amountOfItems) {
        if (amountOfItems < 1) {
            throw new IllegalArgumentException("Amount of items must be greater than 0");
        }

        if (amountOfItems == 1) {
            return VintageConstants.SMALL_PARCEL_BASE_EXPEDITION_PRICE;
        }
        if (amountOfItems <= 5) {
            return VintageConstants.MEDIUM_PARCEL_BASE_EXPEDITION_PRICE;
        }

        return VintageConstants.LARGE_PARCEL_BASE_EXPEDITION_PRICE;
    }

    public BigDecimal getExpeditionTax() {
        return VintageConstants.PARCEL_EXPEDITION_TAX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelCarrier that = (ParcelCarrier) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ParcelCarrier{" +
               "name='" + name + '\'' +
               ", expeditionPriceExpression='" + expeditionPriceExpression + '\'' +
               ", type=" + type +
               '}';
    }
}
