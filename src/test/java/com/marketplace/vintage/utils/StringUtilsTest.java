package com.marketplace.vintage.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

    @Test
    void testFormatCurrency() {
        assertEquals("1.000,00€", StringUtils.formatCurrency(new BigDecimal("1000.00")));
        assertEquals("1.000,00€", StringUtils.formatCurrency(new BigDecimal("1000")));
        assertEquals("1.000,00€", StringUtils.formatCurrency(new BigDecimal("1000.0")));
        assertEquals("1.000,00€", StringUtils.formatCurrency(new BigDecimal("1000.000")));
        assertEquals("50,00€", StringUtils.formatCurrency(new BigDecimal("50.001")));
        assertEquals("50,01€", StringUtils.formatCurrency(new BigDecimal("50.009")));
        assertEquals("49,99€", StringUtils.formatCurrency(new BigDecimal("49.99")));
    }

}