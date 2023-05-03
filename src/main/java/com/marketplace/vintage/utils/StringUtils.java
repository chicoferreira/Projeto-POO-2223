package com.marketplace.vintage.utils;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.item.Item;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringUtils {

    public static String joinQuoted(Collection<String> strings, String delimiter) {
        return joinQuoted(strings, Function.identity(), delimiter);
    }

    public static <T> String joinQuoted(Collection<T> objects, Function<T, String> mapper, String delimiter) {
        return objects.stream()
                      .map(mapper)
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

    public static DateTimeFormatter TRADITIONAL_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
    public static DateTimeFormatter ONLY_DATE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatVintageDate(VintageDate vintageDate, DateTimeFormatter dateFormat) {
        return vintageDate.toDate().format(dateFormat);
    }

    public static String printItem(Item item, int currentYear) {
        return VintageConstants.DISPLAY_ITEM_FORMAT.replace("<id>", item.getAlphanumericId())
                                                   .replace("<itemType>", item.getItemType().getDisplayName())
                                                   .replace("<description>", item.getDescription())
                                                   .replace("<brand>", item.getBrand())
                                                   .replace("<finalPrice>", StringUtils.formatCurrency(item.getFinalPrice(currentYear)))
                                                   .replace("<parcelCarrier>", item.getParcelCarrierName());
    }

}
