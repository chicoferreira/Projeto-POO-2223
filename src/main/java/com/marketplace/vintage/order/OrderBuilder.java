package com.marketplace.vintage.order;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.order.invoice.InvoiceLine;
import com.marketplace.vintage.order.invoice.ItemPriceInvoiceLine;
import com.marketplace.vintage.order.invoice.ItemSatisfactionInvoiceLine;
import com.marketplace.vintage.order.invoice.ParcelShipmentCostInvoiceLine;
import com.marketplace.vintage.utils.VintageDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderBuilder {

    private final String orderId;
    private final UUID userId;
    private final VintageDate orderDate;
    private final List<OrderedItem> orderedItems;
    private final List<InvoiceLine> invoiceLines;

    private OrderBuilder(String orderId, UUID userId, VintageDate orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderedItems = new ArrayList<>();
        this.invoiceLines = new ArrayList<>();
        this.orderDate = orderDate;
    }

    public static OrderBuilder newBuilder(String orderId, UUID userId, VintageDate orderDate) {
        return new OrderBuilder(orderId, userId, orderDate);
    }

    public OrderBuilder addOrderedItem(Item item, int currentYear) {
        this.orderedItems.add(new OrderedItem(item.getOwnerUuid(), item.getAlphanumericId(), item.getParcelCarrierName(), item.getFinalPrice(currentYear)));
        this.invoiceLines.add(new ItemPriceInvoiceLine(item, currentYear));
        return this;
    }

    public OrderBuilder addItemSatisfactionPrice(Item item, BigDecimal satisfactionPrice) {
        this.invoiceLines.add(new ItemSatisfactionInvoiceLine(item, satisfactionPrice));
        return this;
    }

    public OrderBuilder addParcelCarrierShipmentCost(String parcelCarrierName, int itemAmount, BigDecimal shippingCost) {
        this.invoiceLines.add(new ParcelShipmentCostInvoiceLine(parcelCarrierName, itemAmount, shippingCost));
        return this;
    }

    public BigDecimal getTotalPrice() {
        return invoiceLines.stream().reduce(BigDecimal.ZERO, (acc, invoiceLine) -> acc.add(invoiceLine.getPrice()), BigDecimal::add);
    }

    public Order build() {
        return new Order(orderId, userId, orderedItems, invoiceLines, getTotalPrice(), orderDate);
    }

}
