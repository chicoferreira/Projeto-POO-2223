package com.marketplace.vintage.order;

import com.marketplace.vintage.order.invoice.InvoiceLine;
import com.marketplace.vintage.order.invoice.ItemSatisfactionInvoiceLine;
import com.marketplace.vintage.order.invoice.ParcelShipmentCostInvoiceLine;
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

    public Order(Order order) {
        this(order.orderId, order.buyerId, new ArrayList<>(order.orderedItems), new ArrayList<>(order.invoiceLines), order.totalPrice, order.orderDate, order.orderStatus);
        this.deliverDate = order.deliverDate;
    }

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

    public UUID getBuyerId() {
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

    public boolean hasBeenReturned() {
        return this.orderStatus == OrderStatus.RETURNED;
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

    @Override
    public Order clone() {
        return new Order(this);
    }

    public BigDecimal getParcelCarrierShippingCost(String parcelCarrierName) {
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceLine invoiceLine : this.getInvoiceLines()) {
            if (invoiceLine instanceof ParcelShipmentCostInvoiceLine shipmentCostInvoiceLine) {
                if (shipmentCostInvoiceLine.getParcelCarrierName().equals(parcelCarrierName)) {
                    total = total.add(shipmentCostInvoiceLine.getPrice());
                }
            }
        }
        return total;
    }

    public BigDecimal getSumOfSatisfactionPrices() {
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceLine invoiceLine : this.getInvoiceLines()) {
            if (invoiceLine instanceof ItemSatisfactionInvoiceLine) {
                total = total.add(invoiceLine.getPrice());
            }
        }
        return total;
    }

    public BigDecimal getSumOfItemPricesFromSeller(UUID sellerId) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderedItem orderedItem : this.getOrderedItems()) {
            if (orderedItem.getSellerId().equals(sellerId)) {
                total = total.add(orderedItem.getTotalPrice());
            }
        }
        return total;
    }
}
