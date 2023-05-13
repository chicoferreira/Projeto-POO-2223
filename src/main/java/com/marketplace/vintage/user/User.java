package com.marketplace.vintage.user;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {

    private final UUID uuid;
    private final String username;
    private final String email;
    private final String name;
    private final String address;
    private final String taxNumber;
    private final Set<String> itemsBeingSold;
    private final Set<String> shoppingCart;
    private final Set<String> completedOrderIdsList;
    private final Set<String> completedSellsOrderIdsList;

    public User(User user) {
        this(user.uuid, user.username, user.email, user.name, user.address, user.taxNumber);
        this.itemsBeingSold.addAll(user.itemsBeingSold);
        this.shoppingCart.addAll(user.shoppingCart);
        this.completedOrderIdsList.addAll(user.completedOrderIdsList);
        this.completedSellsOrderIdsList.addAll(user.completedSellsOrderIdsList);
    }

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
        this.itemsBeingSold = new HashSet<>();
        this.shoppingCart = new HashSet<>();
        this.completedOrderIdsList = new HashSet<>();
        this.completedSellsOrderIdsList = new HashSet<>();
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

    public List<String> getShoppingCart() {
        return new ArrayList<>(this.shoppingCart);
    }

    public void addItemToShoppingCart(String id) {
        shoppingCart.add(id);
    }

    public void removeItemFromShoppingCart(String id) {
        shoppingCart.remove(id);
    }

    public void cleanShoppingCart() {
        shoppingCart.clear();
    }

    public void addCompletedOrderId(String orderId) {
        completedOrderIdsList.add(orderId);
    }

    public List<String> getCompletedOrderIdsList() {
        return new ArrayList<>(completedOrderIdsList);
    }

    /**
     * Adds an order that contains items that have been sold by this user
     */
    public void addCompletedSellOrderId(String orderId) {
        completedSellsOrderIdsList.add(orderId);
    }

    /**
     * @return a list of all order ids that contain items that have been sold by this user
     */
    public List<String> getCompletedSellsOrderIdsList() {
        return new ArrayList<>(completedSellsOrderIdsList);
    }

    @Override
    public User clone() {
        return new User(this);
    }
}
