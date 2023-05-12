package com.marketplace.vintage.order.invoice;

import com.marketplace.vintage.item.Item;

import java.math.BigDecimal;

public class ItemSatisfactionInvoiceLine extends InvoiceLine {
    public ItemSatisfactionInvoiceLine(Item item, BigDecimal satisfactionPrice) {
        super("Item " + item.getAlphanumericId() + " Condition Satisfaction Service Tax", satisfactionPrice);
    }
}
