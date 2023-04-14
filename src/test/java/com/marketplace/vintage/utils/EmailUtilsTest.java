package com.marketplace.vintage.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailUtilsTest {

    @Test
    public void testIsValidEmail() {
        assertTrue(EmailUtils.isValidEmail("user@mail.com"));
        assertTrue(EmailUtils.isValidEmail("user@mail.pt"));
        assertTrue(EmailUtils.isValidEmail("user123@mail.com"));
        assertTrue(EmailUtils.isValidEmail("user+123@mail.com"));
        assertTrue(EmailUtils.isValidEmail("user-123@mail.com"));
        assertTrue(EmailUtils.isValidEmail("user.123@mail.com"));
        assertTrue(EmailUtils.isValidEmail("user_123@mail.com"));

        assertFalse(EmailUtils.isValidEmail("usermail.com"));
        assertFalse(EmailUtils.isValidEmail("user@mailcom"));
        assertFalse(EmailUtils.isValidEmail("user@mail"));
        assertFalse(EmailUtils.isValidEmail("usermail"));
        assertFalse(EmailUtils.isValidEmail("user@mail."));
        assertFalse(EmailUtils.isValidEmail(""));
        assertFalse(EmailUtils.isValidEmail(null));
        assertFalse(EmailUtils.isValidEmail("user@mail.c"));
        assertFalse(EmailUtils.isValidEmail("user@mail."));
        assertFalse(EmailUtils.isValidEmail("user@mail.co.m"));
    }
}
