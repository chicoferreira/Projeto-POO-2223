package com.marketplace.vintage.user;

import java.util.List;
import java.util.UUID;

public class UserController {

    private final UserManager userManager;

    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    public void addItemToShoppingCart(User user, String itemId) {
        user.addItemToShoppingCart(itemId);
        this.userManager.updateUser(user);
    }

    public void removeItemFromShoppingCart(User user, String itemId) {
        user.removeItemFromShoppingCart(itemId);
        this.userManager.updateUser(user);
    }

    public void addCompletedSellOrderId(User user, String orderId) {
        user.addCompletedSellOrderId(orderId);
        this.userManager.updateUser(user);
    }

    public void addCompletedOrderId(User user, String orderId) {
        user.addCompletedOrderId(orderId);
        this.userManager.updateUser(user);
    }

    public void cleanShoppingCart(User user) {
        user.cleanShoppingCart();
        this.userManager.updateUser(user);
    }

    public boolean existsUserWithEmail(String email) {
        return this.userManager.existsUserWithEmail(email);
    }

    public boolean existsUserWithUsername(String username) {
        return this.userManager.existsUserWithUsername(username);
    }

    public void validateUsername(String username) {
        this.userManager.validateUsername(username);
    }

    public User getUserById(UUID userId) {
        return this.userManager.getUserById(userId);
    }

    public User getUserByUsername(String userUsername) {
        return this.userManager.getUserByUsername(userUsername);
    }

    public User getUserByEmail(String email) {
        return this.userManager.getUserByEmail(email);
    }

    public List<User> getAll() {
        return this.userManager.getAll();
    }

    public User createUser(String username, String email, String name, String address, String taxNumber) {
        return this.userManager.createUser(username, email, name, address, taxNumber);
    }
}
