package com.marketplace.vintage.order.invoice;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class InvoiceLine implements Serializable {

    private final String displayName;
    private final BigDecimal price;

    protected InvoiceLine(String displayName, BigDecimal price) {
        this.displayName = displayName;
        this.price = price;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

}
