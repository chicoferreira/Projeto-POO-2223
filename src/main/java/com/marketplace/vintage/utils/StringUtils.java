package com.marketplace.vintage.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.stream.Collectors;

public class StringUtils {

    public static String joinQuoted(Collection<String> strings, String delimiter) {
        return strings.stream()
                      .map(s -> "'" + s + "'")
                      .collect(Collectors.joining(delimiter));
    }

    public static String formatBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",");
    }

}
