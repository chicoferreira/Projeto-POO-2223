package com.marketplace.vintage.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderedItem implements Serializable {

    private final UUID sellerId;
    private final String itemId;
    private final String parcelCarrierName;
    private final BigDecimal totalPrice;

    public OrderedItem(UUID sellerId, String itemId, String parcelCarrierName, BigDecimal totalPrice) {
        this.sellerId = sellerId;
        this.itemId = itemId;
        this.parcelCarrierName = parcelCarrierName;
        this.totalPrice = totalPrice;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getParcelCarrierName() {
        return parcelCarrierName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}

