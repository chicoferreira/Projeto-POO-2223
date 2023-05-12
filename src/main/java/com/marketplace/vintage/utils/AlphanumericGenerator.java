package com.marketplace.vintage.utils;

import java.util.Random;

public class AlphanumericGenerator {

    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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
            if (arrayFormat[i] == FORMAT_PLACEHOLDER) {
                int randomIndex = RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length());
                arrayFormat[i] = ALPHANUMERIC_CHARACTERS.charAt(randomIndex);
            }
        }

        return new String(arrayFormat);
    }

    public static boolean isOfFormat(String format, String string) {
        if (format.length() != string.length()) {
            return false;
        }

        for (int i = 0; i < format.length(); i++) {
            char formatChar = format.charAt(i);
            char stringChar = string.charAt(i);

            if (formatChar == FORMAT_PLACEHOLDER && ALPHANUMERIC_CHARACTERS.indexOf(stringChar) == -1) {
                return false;
            } else if (formatChar != stringChar) {
                return false;
            }
        }
        return true;
    }
}
