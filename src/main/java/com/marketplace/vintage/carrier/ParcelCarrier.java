package com.marketplace.vintage.carrier;

import com.marketplace.vintage.expression.Expression;
import com.marketplace.vintage.item.ItemType;

import java.util.UUID;

public abstract class ParcelCarrier {

    private final UUID uuid;
    private final String name;
    private Expression expeditionPriceExpression;
    private final ParcelCarrierType type;

    public ParcelCarrier(UUID uuid, String name, Expression expeditionPriceExpression, ParcelCarrierType type) {
        this.uuid = uuid;
        this.name = name;
        this.expeditionPriceExpression = expeditionPriceExpression;
        this.type = type;
    }

    public ParcelCarrier(String name, Expression expeditionPriceExpression, ParcelCarrierType type) {
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

    public Expression getExpeditionPriceExpression() {
        return expeditionPriceExpression;
    }

    public void setExpeditionPriceExpression(Expression expeditionPriceExpression) {
        this.expeditionPriceExpression = expeditionPriceExpression;
    }

    public abstract boolean canDeliverItemType(ItemType itemType);

}
