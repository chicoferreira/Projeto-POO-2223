package com.marketplace.vintage.utils;

import java.util.Random;

public class AlphanumericGenerator {

    private static final char[] ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final Random RANDOM = new Random();
    private static final char FORMAT_PLACEHOLDER = 'X';

    /**
     * This method creates an AlphanumericID depending on the format its given
     * The format must contain the X for the alphanumeric places
     * For example: format = XXX-X => A78-C
     * format = XXX-XXX => GF3-56D
     * format = XXX-XXX-XX => E32-JKL-68
     */
    public static String generateAlphanumericCode(String format) {
        char[] arrayFormat = format.toCharArray();

        for (int i = 0; i < format.length(); i++) {
            int randomIndex = RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length);
            if (arrayFormat[i]  == FORMAT_PLACEHOLDER ) arrayFormat[i] = ALPHANUMERIC_CHARACTERS[randomIndex];
        }
        String idString = new String(arrayFormat);

        return idString;

    }
}
