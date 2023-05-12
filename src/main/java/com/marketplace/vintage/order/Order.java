package com.marketplace.vintage.order;

import com.marketplace.vintage.order.invoice.InvoiceLine;
import com.marketplace.vintage.utils.VintageDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order implements Serializable {

    private final String orderId;
    private final UUID buyerId;
    private final List<OrderedItem> orderedItems;
    private final List<InvoiceLine> invoiceLines;
    private final BigDecimal totalPrice;
    private final VintageDate orderDate;
    private OrderStatus orderStatus;
    private VintageDate deliverDate;

    public Order(String orderId, UUID buyerId, List<OrderedItem> orderedItems, List<InvoiceLine> invoiceLines, BigDecimal totalPrice, VintageDate orderDate) {
        this(orderId, buyerId, orderedItems, invoiceLines, totalPrice, orderDate, OrderStatus.ORDERED);
    }

    public Order(String orderId, UUID buyerId, List<OrderedItem> orderedItems, List<InvoiceLine> invoiceLines, BigDecimal totalPrice, VintageDate orderDate, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.orderedItems = orderedItems;
        this.invoiceLines = invoiceLines;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public UUID geBuyerId() {
        return buyerId;
    }

    public List<OrderedItem> getOrderedItems() {
        return new ArrayList<>(orderedItems); // OrderedItem is immutable
    }

    public List<OrderedItem> getOrderedItemsByParcelCarrier(String parcelCarrierName) {
        return orderedItems.stream().filter(orderedItem -> orderedItem.getParcelCarrierName().equals(parcelCarrierName)).toList();
    }

    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public VintageDate getOrderDate() {
        return orderDate;
    }

    public VintageDate getDeliverDate() {
        if (deliverDate == null) {
            throw new IllegalStateException("Order has not been delivered yet");
        }
        return deliverDate;
    }

    public void setDeliverDate(VintageDate deliverDate) {
        this.deliverDate = deliverDate;
    }
}
