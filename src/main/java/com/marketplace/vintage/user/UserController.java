package com.marketplace.vintage.user;

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
}
