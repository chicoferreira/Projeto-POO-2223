package com.marketplace.vintage.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.stream.Collectors;

public class StringUtils {

    public static String joinQuoted(Collection<String> strings, String delimiter) {
        return strings.stream()
                      .map(s -> "'" + s + "'")
                      .collect(Collectors.joining(delimiter));
    }

    private static final DecimalFormat FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        symbols.setCurrencySymbol("€");

        FORMAT = new DecimalFormat("#,##0.00€", symbols);
    }

    public static String formatCurrency(BigDecimal bigDecimal) {
        return FORMAT.format(bigDecimal);
    }

}
