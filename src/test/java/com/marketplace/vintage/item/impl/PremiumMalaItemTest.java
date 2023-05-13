package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.ItemProperty;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PremiumMalaItemTest {
    @Test
    void getPriceCorrection() {
        BigDecimal basePrice = BigDecimal.valueOf(100);
        int appreciationRateOverYears = 10;
        int collectionYear = 2022;

        PremiumMalaItem premiumMalaItem = new PremiumMalaItem(UUID.randomUUID(), null, 5, null, null, null, basePrice, null, 0, null, collectionYear, appreciationRateOverYears);

        assertEquals(BigDecimal.valueOf(0), premiumMalaItem.getPriceCorrection(2022));
        assertEquals(BigDecimal.valueOf(10.0).compareTo(premiumMalaItem.getPriceCorrection(2023)), 0);
        assertEquals(BigDecimal.valueOf(19.0).compareTo(premiumMalaItem.getPriceCorrection(2024)), 0);

        for (ItemProperty requiredProperty : premiumMalaItem.getItemType().getRequiredProperties()) {
            assertDoesNotThrow(() -> premiumMalaItem.getProperty(requiredProperty, Object.class));
        }
    }
}
