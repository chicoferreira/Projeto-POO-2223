package com.marketplace.vintage.user;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserManagerTest {
    @Test
    void testUserManager() {
        UserManager userManager = new UserManager();

        String testUsername = "cool_username";
        String testEmail = "user@gmail.com";
        String name = "John Doe";
        String address = "123 Main St";
        String taxNumber = "123456789";

        assertFalse(userManager.existsUserWithEmail(testEmail));

        userManager.registerUser(new User(testUsername, testEmail, name, address, taxNumber));

        assertTrue(userManager.existsUserWithEmail(testEmail));

        User user = userManager.getUserByEmail(testEmail);
        assertEquals(testEmail, user.getEmail());
        assertEquals(testUsername, user.getUsername());
        assertEquals(name, user.getName());
        assertEquals(address, user.getAddress());
        assertEquals(taxNumber, user.getTaxNumber());

        User user1 = userManager.getUserByUsername(testUsername);
        assertEquals(testEmail, user1.getEmail());
        assertEquals(testUsername, user1.getUsername());
        assertEquals(name, user1.getName());
        assertEquals(address, user1.getAddress());
        assertEquals(taxNumber, user1.getTaxNumber());

        assertThrowsExactly(EntityNotFoundException.class, () -> userManager.getUserByEmail("__user__@gmail.com"));
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> userManager.registerUser(new User(testUsername, testEmail, name, address, taxNumber)));
    }

    @Test()
    void twoUsersWithSameEmail() {
        UserManager userManager = new UserManager();

        String username = "john_doe";
        String name = "John Doe";
        String address = "123 Main St";
        String taxNumber = "123456789";

        String firstEmail = "user@gmail.com";
        String secondEmail = "USER@GMAIL.COM";

        userManager.registerUser(new User(username, firstEmail, name, address, taxNumber));

        // This should throw an EntityAlreadyExistsException because the email is case-insensitive
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> userManager.registerUser(new User("john_doe2", secondEmail, name, address, taxNumber)));
    }
}
