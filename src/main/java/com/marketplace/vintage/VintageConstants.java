package com.marketplace.vintage;

import java.util.List;

public class VintageConstants {

    public static final String DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING = "basePrice * 0.1 * (1 + tax) * 0.9";
    public static final List<String> DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES = List.of("basePrice", "tax");
    public static final String DISPLAY_ITEM_FORMAT = "[<id>] (<itemType>) <description> (of brand '<brand>') - <finalPrice> (sent by <parcelCarrier>)";

}
