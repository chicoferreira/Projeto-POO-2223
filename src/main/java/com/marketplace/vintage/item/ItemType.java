package com.marketplace.vintage.item;

import java.util.EnumSet;
import java.util.Set;

public enum ItemType {

    MALA("Mala"),
    SAPATILHAS("Sapatilhas"),
    TSHIRT("TShirt"),
    MALA_PREMIUM("Mala Premium", true),
    SAPATILHAS_PREMIUM("Sapatilhas Premium", true);

    private final String displayName;
    private final boolean isPremium;

    ItemType(String displayName) {
        this(displayName, false);
    }

    ItemType(String displayName, boolean isPremium) {
        this.displayName = displayName;
        this.isPremium = isPremium;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isPremium() {
        return isPremium;
    }

    private static final Set<ItemProperty> BASE_ITEM_PROPERTIES = EnumSet.of(ItemProperty.STOCK,
                                                                             ItemProperty.ITEM_CONDITION,
                                                                             ItemProperty.DESCRIPTION,
                                                                             ItemProperty.BRAND,
                                                                             ItemProperty.BASE_PRICE,
                                                                             ItemProperty.PARCEL_CARRIER_NAME);

    // This method needs to be like this because of the inheritance of properties
    // Enums constructors don't support access of static fields
    public Set<ItemProperty> getRequiredProperties() {
        return switch (this) {
            case MALA -> inheritProperties(BASE_ITEM_PROPERTIES, ItemProperty.DIMENSION_AREA, ItemProperty.MATERIAL, ItemProperty.COLLECTION_YEAR, ItemProperty.DEPRECIATION_RATE_OVER_YEARS);
            case SAPATILHAS -> inheritProperties(BASE_ITEM_PROPERTIES, ItemProperty.SAPATILHA_SIZE, ItemProperty.HAS_LACES, ItemProperty.COLOR, ItemProperty.COLLECTION_YEAR);
            case TSHIRT -> inheritProperties(BASE_ITEM_PROPERTIES, ItemProperty.TSHIRT_SIZE, ItemProperty.TSHIRT_PATTERN);
            case MALA_PREMIUM -> inheritProperties(BASE_ITEM_PROPERTIES, ItemProperty.DIMENSION_AREA, ItemProperty.MATERIAL, ItemProperty.COLLECTION_YEAR, ItemProperty.APPRECIATION_RATE_OVER_YEARS);
            case SAPATILHAS_PREMIUM -> inheritProperties(SAPATILHAS.getRequiredProperties(), ItemProperty.APPRECIATION_RATE_OVER_YEARS);
        };
    }

    private static Set<ItemProperty> inheritProperties(Set<ItemProperty> properties, ItemProperty... newProperties) {
        Set<ItemProperty> newSet = EnumSet.copyOf(properties);
        newSet.addAll(Set.of(newProperties));
        return newSet;
    }

    public static ItemType fromDisplayName(String displayName) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getDisplayName().equalsIgnoreCase(displayName)) {
                return itemType;
            }
        }
        throw new IllegalArgumentException("No item type with display name " + displayName + " was found");
    }
}
