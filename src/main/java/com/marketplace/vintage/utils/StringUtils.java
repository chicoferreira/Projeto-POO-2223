package com.marketplace.vintage.utils;

import java.util.Arrays;
import java.util.function.Predicate;

public class StringUtils {

    public static boolean containsOnlyAllowedTokens(String string, Predicate<String> tokenValidator) {
        String[] tokens = string.split("\\s+");

        for (String token : tokens) {
            if (!tokenValidator.test(token)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNumeric(String string) {
        if (string == null) {
            return false;
        }

        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static boolean isOperator(String string) {
        if (string == null) {
            return false;
        }

        String[] operators = {"+", "-", "*", "/", "%", "^", "(", ")"};

        return Arrays.asList(operators).contains(string);
    }

}
