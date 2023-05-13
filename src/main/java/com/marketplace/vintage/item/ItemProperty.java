package com.marketplace.vintage.item;

import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.impl.TshirtItem;

import java.math.BigDecimal;

public enum ItemProperty {

    STOCK(Integer.class),
    ITEM_CONDITION(ItemCondition.class),
    DESCRIPTION(String.class),
    BRAND(String.class),
    BASE_PRICE(BigDecimal.class),
    PARCEL_CARRIER_NAME(String.class),
    DIMENSION_AREA(Integer.class),
    MATERIAL(String.class),
    COLLECTION_YEAR(Integer.class),
    APPRECIATION_RATE_OVER_YEARS(Integer.class),
    DEPRECIATION_RATE_OVER_YEARS(Integer.class),
    SAPATILHA_SIZE(Integer.class),
    HAS_LACES(Boolean.class),
    COLOR(String.class),
    TSHIRT_SIZE(TshirtItem.TshirtItemSize.class),
    TSHIRT_PATTERN(TshirtItem.TshirtItemPattern.class),
    ;

    private final Class<?> valueClass;

    ItemProperty(Class<?> valueClass) {
        this.valueClass = valueClass;
    }

    public Class<?> getValueClass() {
        return valueClass;
    }
}
