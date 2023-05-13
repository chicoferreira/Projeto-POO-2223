package com.marketplace.vintage.carrier;

public class ParcelCarrierController {

    private final ParcelCarrierManager parcelCarrierManager;

    public ParcelCarrierController(ParcelCarrierManager parcelCarrierManager) {
        this.parcelCarrierManager = parcelCarrierManager;
    }

    public void setCarrierExpeditionPriceExpression(ParcelCarrier carrier, String formula) {
        carrier.setExpeditionPriceExpression(formula);
        this.parcelCarrierManager.updateParcelCarrier(carrier.getName(), carrier);
    }

    public void addDeliveredOrderId(ParcelCarrier parcelCarrier, String orderId) {
        parcelCarrier.addDeliveredOrder(orderId);
        this.parcelCarrierManager.updateParcelCarrier(parcelCarrier.getName(), parcelCarrier);
    }
}
