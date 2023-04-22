package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.item.condition.ItemConditions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SapatilhasItemTest {

    @Test
    void getPriceCorrection() {
        BigDecimal basePrice = BigDecimal.valueOf(100);

        ItemCondition usedItemCondition = ItemConditions.createUsed(5, 1);
        SapatilhasItem sapatilhasItem = new SapatilhasItem(usedItemCondition, null, null, null, basePrice, null, 0, false, "Red", 2022);

        assertEquals(sapatilhasItem.getPriceCorrection(2022).compareTo(BigDecimal.valueOf(-25)), 0);
    }
}