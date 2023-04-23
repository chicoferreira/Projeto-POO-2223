package com.marketplace.vintage.utils;

import java.util.Random;

public class AlphanumericGenerator {

    /**
     * This method creates an AlphanumericID depending on the format its given
     * For example: format = XXX-X => A78-C
     *              format = XXX-XXX => GF3-56D
     *              format = XXX-XXX-XX => E32-JKL-68
     */

    public static String generateAlphanumericID(String format) {

        char [] character = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        char [] arrayFormat = format.toCharArray();

        Random random = new Random();
        for(int i = 0; i < format.length(); i++) {
            int randomIndex = random.nextInt(character.length);
            if(arrayFormat[i] != '-') arrayFormat[i] = character[randomIndex];
        }
        String idString = new String(arrayFormat);

        return idString;

    }
}
