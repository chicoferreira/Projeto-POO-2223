package com.marketplace.vintage.item;

public enum ItemType {

    MALA, SAPATILHAS, TSHIRT, MALA_PREMIUM(true), SAPATILHAS_PREMIUM(true);

    private final boolean isPremium;

    ItemType() {
        this(false);
    }

    ItemType(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public boolean isPremium() {
        return isPremium;
    }
}
