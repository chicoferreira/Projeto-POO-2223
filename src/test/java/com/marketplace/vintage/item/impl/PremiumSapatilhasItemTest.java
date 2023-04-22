package com.marketplace.vintage.item.impl;

import com.marketplace.vintage.item.condition.ItemCondition;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PremiumSapatilhasItemTest {

    @Test
    void getPriceCorrection() {
        BigDecimal basePrice = BigDecimal.valueOf(100);
        int collectionYear = 2022;
        int appreciationRateOverYears = 10;

        PremiumSapatilhasItem premiumSapatilhasItem = new PremiumSapatilhasItem(null, null, null, null, basePrice, null, 0, false, "Red", collectionYear, appreciationRateOverYears);

        assertEquals(BigDecimal.valueOf(0).compareTo(premiumSapatilhasItem.getPriceCorrection(2022)), 0);
        assertEquals(BigDecimal.valueOf(10).compareTo(premiumSapatilhasItem.getPriceCorrection(2023)), 0);
        assertEquals(BigDecimal.valueOf(19).compareTo(premiumSapatilhasItem.getPriceCorrection(2024)), 0);
    }
}