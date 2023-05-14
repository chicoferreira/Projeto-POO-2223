package com.marketplace.vintage;

import com.marketplace.vintage.utils.VintageDate;

import java.math.BigDecimal;
import java.util.List;

public class VintageConstants {

    public static final String EXPEDITION_PRICE_EXPRESSION_BASE_PRICE_VARIABLE = "basePrice";
    public static final String EXPEDITION_PRICE_EXPRESSION_TAX_VARIABLE = "tax";

    public static final String DEFAULT_EXPEDITION_PRICE_EXPRESSION_STRING = EXPEDITION_PRICE_EXPRESSION_BASE_PRICE_VARIABLE + " * (1 + " + EXPEDITION_PRICE_EXPRESSION_TAX_VARIABLE + ") * 0.9";
    public static final List<String> DEFAULT_EXPEDITION_PRICE_EXPRESSION_VARIABLES = List.of(EXPEDITION_PRICE_EXPRESSION_BASE_PRICE_VARIABLE, EXPEDITION_PRICE_EXPRESSION_TAX_VARIABLE);
    public static final String DISPLAY_ITEM_FORMAT = "[<id>] (<itemType>) <description> (of brand '<brand>') - <finalPrice> (sent by <parcelCarrier>)";
    public static final String DISPLAY_USER_FORMAT = "[<id>] <name> (<email>) <username>";

    public static final String ITEM_CONDITION_NEW_DISPLAY_STRING = "New";
    public static final String ITEM_CONDITION_USED_DISPLAY_STRING = "Used (<conditionLevel>/10, <numberOfPreviousOwners> previous owners)";

    public static final VintageDate VINTAGE_START_DATE = VintageDate.of(1, 1, 2023);

    public static final BigDecimal SMALL_PARCEL_BASE_EXPEDITION_PRICE = BigDecimal.valueOf(0.5);
    public static final BigDecimal MEDIUM_PARCEL_BASE_EXPEDITION_PRICE = BigDecimal.valueOf(1.25);
    public static final BigDecimal LARGE_PARCEL_BASE_EXPEDITION_PRICE = BigDecimal.valueOf(3);
    public static final BigDecimal PARCEL_EXPEDITION_TAX = BigDecimal.valueOf(0.10); // 10%

    public static final int DAYS_TO_DELIVER_ORDER = 3;
    public static final int DAYS_TO_RETURN_ORDER = 2;

    public static final String PERSISTENT_PROGRAM_STATE_SAVE_PATH = "vintage_persistent_data.bin";
    public static final String SCRIPTS_FILE_PATH = "scripts.txt";

}
