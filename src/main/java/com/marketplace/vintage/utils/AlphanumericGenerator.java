package com.marketplace.vintage.utils;

import java.util.Random;

public class AlphanumericGenerator {

    /**
     * This method creates an AlphanumericID divided by an '-' every 3 symbols
     * For example: lenght = 4 => A78-C
     *              length = 6 => GF3-56D
     *              length = 8 => E32-JKL-68
     */

    public static String generateAlphanumericID(int length) {

        String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String alphanumeric = "";

        Random random = new Random();
        for(int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(character.length());
            alphanumeric += character.indexOf(randomIndex);
            if(i == 2) alphanumeric += '-';
        }

        return alphanumeric;

    }
}
