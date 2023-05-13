package com.marketplace.vintage.carrier;

import com.marketplace.vintage.item.ItemType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    public void registerParcelCarrier(@NotNull ParcelCarrier carrier) {
        this.parcelCarrierManager.registerParcelCarrier(carrier);
    }

    public ParcelCarrier getCarrierByName(String name) {
        return this.parcelCarrierManager.getCarrierByName(name);
    }

    public boolean containsCarrierByName(String name) {
        return this.parcelCarrierManager.containsCarrierByName(name);
    }

    public List<ParcelCarrier> getAll() {
        return this.parcelCarrierManager.getAll();
    }

    public List<ParcelCarrier> getAllCompatibleWith(ItemType itemType) {
        return this.parcelCarrierManager.getAllCompatibleWith(itemType);
    }
}
