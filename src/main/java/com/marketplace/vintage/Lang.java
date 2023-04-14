package com.marketplace.vintage;

public enum Lang {

    USED_ITEM_CONDITION_DISPLAY_STRING("Usado (<conditionLevel>/10, <numberOfPreviousOwners> donos anteriores)"),
    NEW_ITEM_CONDITION_DISPLAY_STRING("Novo"),
    VIEW_TYPE_USER_DISPLAY_NAME("User"),
    VIEW_TYPE_CARRIER_DISPLAY_NAME("Parcel Carrier"),
    ;

    private final String value;

    Lang(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }

    public String apply(Object... strings) {
        if (strings.length % 2 != 0) {
            throw new IllegalArgumentException("strings.length must be even");
        }

        String result = this.value;

        for (int i = 0; i < strings.length; i += 2) {
            String key = "<" + strings[i] + ">";
            String replacement = String.valueOf(strings[i + 1]);

            result = result.replace(key, replacement);
        }

        return result;
    }
}
