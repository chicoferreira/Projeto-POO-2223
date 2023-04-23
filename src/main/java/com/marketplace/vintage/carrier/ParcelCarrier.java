package com.marketplace.vintage.carrier;

import com.marketplace.vintage.item.ItemType;

import java.util.UUID;

public abstract class ParcelCarrier {

    private final UUID uuid;
    private final String name;
    private final ParcelCarrierType type;

    public ParcelCarrier(UUID uuid, String name, ParcelCarrierType type) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
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

    public abstract boolean canDeliverItemType(ItemType itemType);

}
