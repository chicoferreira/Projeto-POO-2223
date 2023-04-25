package com.marketplace.vintage.carrier;

import com.marketplace.vintage.item.ItemType;

import java.util.UUID;

public abstract class ParcelCarrier {

    private final UUID uuid;
    private final String name;
    private String expeditionPriceExpression;
    private final ParcelCarrierType type;

    public ParcelCarrier(UUID uuid, String name, String expeditionPriceExpression, ParcelCarrierType type) {
        this.uuid = uuid;
        this.name = name;
        this.expeditionPriceExpression = expeditionPriceExpression;
        this.type = type;
    }

    public ParcelCarrier(String name, String expeditionPriceExpression, ParcelCarrierType type) {
        this(UUID.randomUUID(), name, expeditionPriceExpression, type);
    }

    public UUID getId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ParcelCarrierType getType() {
        return type;
    }

    public String getExpeditionPriceExpression() {
        return expeditionPriceExpression;
    }

    /**
     * Set the price expression for this carrier, assuming the expression is valid
     */
    public void setExpeditionPriceExpression(String expeditionPriceExpression) {
        this.expeditionPriceExpression = expeditionPriceExpression;
    }

    public abstract boolean canDeliverItemType(ItemType itemType);

}
