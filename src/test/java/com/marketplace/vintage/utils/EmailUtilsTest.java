package com.marketplace.vintage.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailUtilsTest {

    @Test
    public void testIsValidEmail() {
        String validEmail = "user@mail.com";
        assertTrue(EmailUtils.isValidEmail(validEmail));

        String validEmail2 = "user@mail.pt";
        assertTrue(EmailUtils.isValidEmail(validEmail2));

        String invalidEmail = "usermail.com";
        assertFalse(EmailUtils.isValidEmail(invalidEmail));

        String invalidEmail2 = "user@mailcom";
        assertFalse(EmailUtils.isValidEmail(invalidEmail2));

        String invalidEmail3 = "user@mail";
        assertFalse(EmailUtils.isValidEmail(invalidEmail3));

        String invalidEmail4 = "usermail";
        assertFalse(EmailUtils.isValidEmail(invalidEmail4));

        String invalidEmail5 = "user@mail.";
        assertFalse(EmailUtils.isValidEmail(invalidEmail5));
    }
}
