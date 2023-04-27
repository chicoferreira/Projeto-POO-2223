package com.marketplace.vintage.item;

import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.impl.MalaItem;
import com.marketplace.vintage.item.impl.PremiumMalaItem;
import com.marketplace.vintage.item.impl.PremiumSapatilhasItem;
import com.marketplace.vintage.item.impl.SapatilhasItem;
import com.marketplace.vintage.item.impl.TshirtItem;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class ItemFactory {

    private final ItemManager itemManager;

    public ItemFactory(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public Item createItem(ItemType itemType, Map<ItemProperty, Object> itemProperties) {
        return switch (itemType) {
            case MALA -> createMala(itemProperties);
            case SAPATILHAS -> createSapatilhas(itemProperties);
            case TSHIRT -> createTshirt(itemProperties);
            case MALA_PREMIUM -> createMalaPremium(itemProperties);
            case SAPATILHAS_PREMIUM -> createSapatilhasPremium(itemProperties);
        };
    }

    private Item createMala(Map<ItemProperty, Object> itemProperties) {
        return new MalaItem(itemManager.generateUniqueCode(),
                            getProperty(itemProperties, ItemProperty.ITEM_CONDITION, ItemCondition.class),
                            getProperty(itemProperties, ItemProperty.DESCRIPTION, String.class),
                            getProperty(itemProperties, ItemProperty.BRAND, String.class),
                            getProperty(itemProperties, ItemProperty.BASE_PRICE, BigDecimal.class),
                            getProperty(itemProperties, ItemProperty.PARCEL_CARRIER_UUID, UUID.class),
                            getProperty(itemProperties, ItemProperty.DIMENSION_AREA, Integer.class),
                            getProperty(itemProperties, ItemProperty.MATERIAL, String.class),
                            getProperty(itemProperties, ItemProperty.COLLECTION_YEAR, Integer.class),
                            getProperty(itemProperties, ItemProperty.APPRECIATION_RATE_OVER_YEARS, Integer.class));
    }

    private Item createSapatilhas(Map<ItemProperty, Object> itemProperties) {
        return new SapatilhasItem(itemManager.generateUniqueCode(),
                                  getProperty(itemProperties, ItemProperty.ITEM_CONDITION, ItemCondition.class),
                                  getProperty(itemProperties, ItemProperty.DESCRIPTION, String.class),
                                  getProperty(itemProperties, ItemProperty.BRAND, String.class),
                                  getProperty(itemProperties, ItemProperty.BASE_PRICE, BigDecimal.class),
                                  getProperty(itemProperties, ItemProperty.PARCEL_CARRIER_UUID, UUID.class),
                                  getProperty(itemProperties, ItemProperty.SAPATILHA_SIZE, Integer.class),
                                  getProperty(itemProperties, ItemProperty.HAS_LACES, Boolean.class),
                                  getProperty(itemProperties, ItemProperty.COLOR, String.class),
                                  getProperty(itemProperties, ItemProperty.COLLECTION_YEAR, Integer.class));
    }

    private Item createTshirt(Map<ItemProperty, Object> itemProperties) {
        return new TshirtItem(itemManager.generateUniqueCode(),
                              getProperty(itemProperties, ItemProperty.ITEM_CONDITION, ItemCondition.class),
                              getProperty(itemProperties, ItemProperty.DESCRIPTION, String.class),
                              getProperty(itemProperties, ItemProperty.BRAND, String.class),
                              getProperty(itemProperties, ItemProperty.BASE_PRICE, BigDecimal.class),
                              getProperty(itemProperties, ItemProperty.PARCEL_CARRIER_UUID, UUID.class),
                              getProperty(itemProperties, ItemProperty.TSHIRT_SIZE, TshirtItem.TshirtItemSize.class),
                              getProperty(itemProperties, ItemProperty.TSHIRT_PATTERN, TshirtItem.TshirtItemPattern.class));
    }

    private Item createMalaPremium(Map<ItemProperty, Object> itemProperties) {
        return new PremiumMalaItem(itemManager.generateUniqueCode(),
                                   getProperty(itemProperties, ItemProperty.ITEM_CONDITION, ItemCondition.class),
                                   getProperty(itemProperties, ItemProperty.DESCRIPTION, String.class),
                                   getProperty(itemProperties, ItemProperty.BRAND, String.class),
                                   getProperty(itemProperties, ItemProperty.BASE_PRICE, BigDecimal.class),
                                   getProperty(itemProperties, ItemProperty.PARCEL_CARRIER_UUID, UUID.class),
                                   getProperty(itemProperties, ItemProperty.DIMENSION_AREA, Integer.class),
                                   getProperty(itemProperties, ItemProperty.MATERIAL, String.class),
                                   getProperty(itemProperties, ItemProperty.COLLECTION_YEAR, Integer.class),
                                   getProperty(itemProperties, ItemProperty.APPRECIATION_RATE_OVER_YEARS, Integer.class));
    }

    private Item createSapatilhasPremium(Map<ItemProperty, Object> itemProperties) {
        return new PremiumSapatilhasItem(itemManager.generateUniqueCode(),
                                         getProperty(itemProperties, ItemProperty.ITEM_CONDITION, ItemCondition.class),
                                         getProperty(itemProperties, ItemProperty.DESCRIPTION, String.class),
                                         getProperty(itemProperties, ItemProperty.BRAND, String.class),
                                         getProperty(itemProperties, ItemProperty.BASE_PRICE, BigDecimal.class),
                                         getProperty(itemProperties, ItemProperty.PARCEL_CARRIER_UUID, UUID.class),
                                         getProperty(itemProperties, ItemProperty.SAPATILHA_SIZE, Integer.class),
                                         getProperty(itemProperties, ItemProperty.HAS_LACES, Boolean.class),
                                         getProperty(itemProperties, ItemProperty.COLOR, String.class),
                                         getProperty(itemProperties, ItemProperty.COLLECTION_YEAR, Integer.class),
                                         getProperty(itemProperties, ItemProperty.APPRECIATION_RATE_OVER_YEARS, Integer.class));
    }

    private static <T> T getProperty(Map<ItemProperty, Object> itemProperties, ItemProperty itemProperty, Class<T> propertyClass /* needs to receive class to cast generic easily */) {
        if (!itemProperties.containsKey(itemProperty)) {
            throw new IllegalArgumentException("The item property " + itemProperty + " is required");
        }

        if (!itemProperty.getValueClass().equals(propertyClass)) {
            throw new IllegalArgumentException("The item property " + itemProperty + " is not of type " + propertyClass);
        }

        return propertyClass.cast(itemProperties.get(itemProperty));
    }
}
