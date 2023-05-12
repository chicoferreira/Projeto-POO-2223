package com.marketplace.vintage.carrier;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.item.ItemType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ParcelCarrier implements Serializable {

    private final String name;
    private String expeditionPriceExpression;
    private final ParcelCarrierType type;
    private final List<String> deliveredOrderIds;

    public ParcelCarrier(String name, String expeditionPriceExpression, ParcelCarrierType type) {
        this.name = name;
        this.expeditionPriceExpression = expeditionPriceExpression;
        this.type = type;
        this.deliveredOrderIds = new ArrayList<>();
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

    /**
     * @return the list of order ids that have been delivered by this carrier
     * Note: the order may contain items that have been delivered by another carrier as well
     */
    public List<String> getDeliveredOrders() {
        return new ArrayList<>(deliveredOrderIds);
    }

    /**
     * @param orderId the id of the order that contains items that have been delivered by this carrier
     */
    public void addDeliveredOrder(String orderId) {
        deliveredOrderIds.add(orderId);
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
