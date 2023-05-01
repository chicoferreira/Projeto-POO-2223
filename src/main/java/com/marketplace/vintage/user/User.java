package com.marketplace.vintage.user;

import com.marketplace.vintage.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private final String username;
    private final String email;
    private final String name;
    private final String address;
    private final String taxNumber;
    private final List<String> itemsBeingSold;
    private final List<String> shoppingCart;
    private final List<Order> ordersMade;

    public User(String username, String email, String name, String address, String taxNumber) {
        this(UUID.randomUUID(), username, email, name, address, taxNumber);
    }

    public User(UUID uuid, String username, String email, String name, String address, String taxNumber) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.name = name;
        this.address = address;
        this.taxNumber = taxNumber;
        this.itemsBeingSold = new ArrayList<>();
        this.shoppingCart = new ArrayList<>();
        this.ordersMade = new ArrayList<>();
    }

    public UUID getId() {
        return uuid;
    }

    public String getUsername() {
        return username;
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

    public List<String> getItemsBeingSold() {
        return new ArrayList<>(itemsBeingSold);
    }

    public void addItemBeingSold(String itemId) {
        itemsBeingSold.add(itemId);
    }

    public void removeItemBeingSold(String itemId) {
        itemsBeingSold.remove(itemId);
    }

    public List<String> getShoppingCart() { return this.shoppingCart; }

    public void addItemToShoppingCart(String id) { shoppingCart.add(id); }

    public void removeItemFromShoppingCart(String id) { shoppingCart.remove(id); }

    public List<Order> getOrdersMade() { return new ArrayList<>(ordersMade); }
    public void addOrder(Order order) { ordersMade.add(order); }
}
