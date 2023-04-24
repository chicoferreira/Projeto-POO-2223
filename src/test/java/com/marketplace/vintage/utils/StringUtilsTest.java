package com.marketplace.vintage.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilsTest {

    @Test
    void containsOnlyAllowedTokens() {
        assertTrue(StringUtils.containsOnlyAllowedTokens("1 2 3", StringUtils::isNumeric));
        assertFalse(StringUtils.containsOnlyAllowedTokens("1 2 3 a", StringUtils::isNumeric));
        assertFalse(StringUtils.containsOnlyAllowedTokens("1 2 3 +", StringUtils::isNumeric));

        String string = "basePrice * 0.05";
        List<String> allowedVariables = Arrays.asList("basePrice", "tax");
        Predicate<String> isVariable = variable -> allowedVariables.contains(variable) || StringUtils.isNumeric(variable) || StringUtils.isOperator(variable);
        assertTrue(StringUtils.containsOnlyAllowedTokens(string, isVariable));
    }

    @Test
    void isNumeric() {
        assertTrue(StringUtils.isNumeric("1"));
        assertTrue(StringUtils.isNumeric("1.0"));
        assertFalse(StringUtils.isNumeric("a"));
        assertFalse(StringUtils.isNumeric("+"));
    }

    @Test
    void isOperator() {
        assertTrue(StringUtils.isOperator("+"));
        assertTrue(StringUtils.isOperator("-"));
        assertFalse(StringUtils.isOperator("1"));
        assertFalse(StringUtils.isOperator("a"));
    }
}
