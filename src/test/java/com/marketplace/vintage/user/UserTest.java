package com.marketplace.vintage.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    @Test
    void testUser() {
        String email = "user@gmail.com";
        String name = "User";
        String address = "123 Main St.";
        String taxNumber = "123456789";

        User user = new User(email, name, address, taxNumber);

        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(address, user.getAddress());
        assertEquals(taxNumber, user.getTaxNumber());

        // UUID is generated automatically
        assertNotNull(user.getId());
        assertEquals(36, user.getId().toString().length());
    }
}
