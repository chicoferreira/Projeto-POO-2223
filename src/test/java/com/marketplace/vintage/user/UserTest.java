package com.marketplace.vintage.user;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void testUser() {
        String email = "USER@GMAIL.COM";
        String name = "User";
        String address = "123 Main St.";
        String taxNumber = "123456789";

        User user = new User(email, name, address, taxNumber);

        // Email is stored in lowercase, because it is case-insensitive
        assert user.getEmail().equals(email.toLowerCase());
        assert user.getName().equals(name);
        assert user.getAddress().equals(address);
        assert user.getTaxNumber().equals(taxNumber);

        // UUID is generated automatically
        assert user.getId() != null;
    }
}
