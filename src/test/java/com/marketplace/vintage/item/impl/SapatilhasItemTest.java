package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.condition.ItemConditions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SapatilhasItemTest {

    @Test
    void getPriceCorrection() {
        BigDecimal basePrice = BigDecimal.valueOf(100);

        ItemCondition usedItemCondition = ItemConditions.createUsed(5, 1);
        SapatilhasItem sapatilhasItem = new SapatilhasItem(UUID.randomUUID(), null, 5, usedItemCondition, null, null, basePrice, null, 0, false, "Red", 2022);

        assertEquals(sapatilhasItem.getPriceCorrection(2022).compareTo(BigDecimal.valueOf(-25)), 0);

        for (ItemProperty requiredProperty : sapatilhasItem.getItemType().getRequiredProperties()) {
            assertDoesNotThrow(() -> sapatilhasItem.getProperty(requiredProperty, Object.class));
        }
    }
}
