package com.marketplace.vintage.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringUtils {

    public static String joinQuoted(Collection<String> strings, String delimiter) {
        return strings.stream()
                      .map(s -> "'" + s + "'")
                      .collect(Collectors.joining(delimiter));
    }

    private static final NumberFormat FORMAT = NumberFormat.getCurrencyInstance(Locale.GERMANY);

    public static String formatBigDecimal(BigDecimal bigDecimal) {
        return FORMAT.format(bigDecimal);
    }

}
