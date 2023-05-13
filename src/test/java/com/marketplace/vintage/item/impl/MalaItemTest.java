package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.ItemProperty;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MalaItemTest {

    @Test
    void getPriceCorrection() {
        BigDecimal basePrice = BigDecimal.valueOf(100);
        int appreciationRateOverYears = 10;
        int collectionYear = 2022;
        int dimensionArea = 0;
        String material = "Material";

        MalaItem malaItem = new MalaItem(UUID.randomUUID(), null, 5, null, null, null, basePrice, null, dimensionArea, material, collectionYear, appreciationRateOverYears);

        assertEquals(BigDecimal.valueOf(0), malaItem.getPriceCorrection(2022));
        assertEquals(BigDecimal.valueOf(-10.0), malaItem.getPriceCorrection(2023));
        assertEquals(malaItem.getPriceCorrection(2024).compareTo(BigDecimal.valueOf(-19.0)), 0);

        for (ItemProperty requiredProperty : malaItem.getItemType().getRequiredProperties()) {
            assertDoesNotThrow(() -> malaItem.getProperty(requiredProperty, Object.class));
        }
    }
}