package com.marketplace.vintage.order.invoice;

import com.marketplace.vintage.item.Item;

public class ItemPriceInvoiceLine extends InvoiceLine {
    public ItemPriceInvoiceLine(Item item, int currentYear) {
        super("Item " +
                        item.getAlphanumericId() +
                        " (" + item.getItemType().getDisplayName() + ") " +
                        item.getDescription(),
                item.getFinalPrice(currentYear));
    }
}
