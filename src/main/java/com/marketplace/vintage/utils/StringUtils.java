package com.marketplace.vintage.utils;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    public static String joinQuoted(List<String> strings, String delimiter) {
        return strings.stream()
                      .map(s -> "'" + s + "'")
                      .collect(Collectors.joining(delimiter));
    }

}
