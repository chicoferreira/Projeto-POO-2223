package com.marketplace.vintage.order.invoice;

import java.math.BigDecimal;

public class ParcelShipmentCostInvoiceLine extends InvoiceLine {
    public ParcelShipmentCostInvoiceLine(String parcelCarrierName, int itemAmount, BigDecimal totalShippingCost) {
        super(parcelCarrierName + " Shipping Cost (" + itemAmount + " items)", totalShippingCost);
    }
}
