package com.marketplace.vintage.item.impl;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MalaItemTest {

    @Test
    void getPriceCorrection() {
        BigDecimal basePrice = BigDecimal.valueOf(100);
        int appreciationRateOverYears = 10;

        MalaItem malaItem = new MalaItem(null, null, null, null, basePrice, null, 0, null, 2022, appreciationRateOverYears);

        assertEquals(basePrice, malaItem.getPriceCorrection(2022));
        assertEquals(BigDecimal.valueOf(110.0).compareTo(malaItem.getPriceCorrection(2023)), 0);
        assertEquals(BigDecimal.valueOf(121.0).compareTo(malaItem.getPriceCorrection(2024)), 0);
    }
}