package com.marketplace.vintage.user;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private final String email;
    private final String name;
    private final String address;
    private final String taxNumber;

    public User(String email, String name, String address, String taxNumber) {
        this(UUID.randomUUID(), email, name, address, taxNumber);
    }

    public User(UUID uuid, String email, String name, String address, String taxNumber) {
        this.uuid = uuid;
        this.email = email.toLowerCase();
        this.name = name;
        this.address = address;
        this.taxNumber = taxNumber;
    }

    public UUID getId() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTaxNumber() {
        return taxNumber;
    }
}
