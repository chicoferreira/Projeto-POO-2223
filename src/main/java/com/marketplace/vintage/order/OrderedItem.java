package com.marketplace.vintage.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderedItem implements Serializable {

    private final String itemId;
//    private final int quantity;
    private final String parcelCarrierName;
    private final BigDecimal totalPrice;

    public OrderedItem(String itemId, int quantity, String parcelCarrierName, BigDecimal totalPrice) {
        this.itemId = itemId;
//        this.quantity = quantity;
        this.parcelCarrierName = parcelCarrierName;
        this.totalPrice = totalPrice;
    }

    public String getItemId() {
        return itemId;
    }

    // TODO: make easy to get quantity from the shopping bag

//    public int getQuantity() {
//        return quantity;
//    }

    public String getParcelCarrierName() {
        return parcelCarrierName;
    }

    /**
     * @return the price of item * quantity
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}

