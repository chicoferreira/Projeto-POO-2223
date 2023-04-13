package com.marketplace.vintage.user;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class UserManagerTest {

    @Test
    void testUserManager() {
        UserManager userManager = new UserManager();

        UUID testUUID = UUID.randomUUID();
        String testEmail = "user@gmail.com";
        String name = "John Doe";
        String address = "123 Main St";
        String taxNumber = "123456789";

        userManager.registerUser(new User(testUUID, testEmail, name, address, taxNumber));

        User user = userManager.getUserByEmail(testEmail);
        assertEquals(testEmail, user.getEmail());
        assertEquals(testUUID, user.getId());
        assertEquals(name, user.getName());
        assertEquals(address, user.getAddress());
        assertEquals(taxNumber, user.getTaxNumber());

        User user1 = userManager.getUserById(testUUID);
        assertEquals(testEmail, user1.getEmail());
        assertEquals(testUUID, user1.getId());
        assertEquals(name, user1.getName());
        assertEquals(address, user1.getAddress());
        assertEquals(taxNumber, user1.getTaxNumber());

        assertThrowsExactly(EntityNotFoundException.class, () -> userManager.getUserByEmail("__user__@gmail.com"));
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> userManager.registerUser(new User(testEmail, name, address, taxNumber)));
    }
}
