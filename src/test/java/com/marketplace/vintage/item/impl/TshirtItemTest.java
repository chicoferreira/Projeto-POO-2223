package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.condition.ItemConditions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TshirtItemTest {

    @Test
    void getPriceCorrection() {
        ItemCondition usedItemCondition = ItemConditions.createUsed(5, 1);

        TshirtItem tshirtItem = new TshirtItem(
                UUID.randomUUID(), null,
                1,
                usedItemCondition,
                "description",
                "brand",
                BigDecimal.valueOf(100),
                null,
                TshirtItem.TshirtItemSize.SMALL,
                TshirtItem.TshirtItemPattern.STRIPES
        );

        assertEquals(BigDecimal.valueOf(-50.0), tshirtItem.getPriceCorrection(2022));

        for (ItemProperty requiredProperty : tshirtItem.getItemType().getRequiredProperties()) {
            assertDoesNotThrow(() -> tshirtItem.getProperty(requiredProperty, Object.class));
        }
    }
}
