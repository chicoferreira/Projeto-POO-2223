package com.marketplace.vintage.utils;

import java.util.Random;
import java.util.stream.IntStream;

public class AlphanumericGenerator {

    private String generateAlphanumericID(int length) {

        String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String alphanumeric = new String();

        Random random = new Random();
        for(int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(character.length());
            alphanumeric+= character.indexOf(randomIndex);
            if(i == 2) alphanumeric += '-';
        }

        return alphanumeric;

    }
}
