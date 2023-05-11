package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.*;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderController;
import com.marketplace.vintage.order.OrderFactory;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.utils.VintageDate;

import java.util.List;
import java.util.Map;

public class VintageController {

    private final ItemManager itemManager;
    private final ItemFactory itemFactory;
    private final OrderManager orderManager;
    private final OrderController orderController;
    private final VintageTimeManager vintageTimeManager;
    private final ParcelCarrierManager parcelCarrierManager;
    private final ExpressionSolver expressionSolver;
    private final OrderFactory orderFactory;
    private final UserManager userManager;

    public VintageController(ItemManager itemManager, ItemFactory itemFactory, OrderManager orderManager, OrderController orderController, VintageTimeManager vintageTimeManager, ParcelCarrierManager parcelCarrierManager, ExpressionSolver expressionSolver, OrderFactory orderFactory, UserManager userManager) {
        this.itemManager = itemManager;
        this.itemFactory = itemFactory;
        this.orderManager = orderManager;
        this.orderController = orderController;
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
        this.expressionSolver = expressionSolver;
        this.orderFactory = orderFactory;
        this.userManager = userManager;
    }

    public Item registerNewItem(User owner, ItemType itemType, Map<ItemProperty, Object> properties) {
        String uniqueCode = itemManager.generateUniqueCode();
        Item item = itemFactory.createItem(owner.getId(), uniqueCode, itemType, properties);
        itemManager.registerItem(item);

        owner.addItemBeingSold(item.getAlphanumericId());

        return item;
    }

    public Order makeOrder(User user) {
        List<String> shoppingCart = user.getShoppingCart();
        if (shoppingCart.isEmpty()) {
            throw new IllegalStateException("The shopping cart is empty.");
        }

        List<Item> itemList = shoppingCart.stream().map(itemManager::getItem).toList();
        if (itemList.stream().anyMatch(item -> item.getOwnerUuid().equals(user.getId()))) {
            throw new IllegalStateException("The shopping cart contains items that are not owned by the user.");
        }

        Order order = orderFactory.buildOrder(orderManager.generateUniqueOrderId(), user.getId(), itemList);

        orderManager.registerOrder(order);
        user.addCompletedOrderId(order.getOrderId());

        user.cleanShoppingCart();

        return order;
    }

    public VintageDate getCurrentDate() {
        return this.vintageTimeManager.getCurrentDate();
    }

    /**
     * @param days number of days to jump
     * @return the new date
     */
    public VintageDate jumpTime(int days) {
        VintageDate newDate = this.vintageTimeManager.advanceTime(days);

        this.orderController.notifyTimeChange(newDate);
        // TODO: also notify script manager

        return newDate;
    }

    public int getCurrentYear() {
        return this.vintageTimeManager.getCurrentYear();
    }

    public boolean containsItemById(String itemId) {
        return this.itemManager.containsItemById(itemId);
    }

    public Item getItem(String itemId) {
        return this.itemManager.getItem(itemId);
    }

    public Order getOrder(String orderId) {
        return this.orderManager.getOrder(orderId);
    }

    public List<ParcelCarrier> getAllParcelCarriersCompatibleWith(ItemType itemType) {
        return this.parcelCarrierManager.getAllCompatibleWith(itemType);
    }

    public boolean containsCarrierByName(String carrierName) {
        return this.parcelCarrierManager.containsCarrierByName(carrierName);
    }

    public ParcelCarrier getCarrierByName(String carrierName) {
        return this.parcelCarrierManager.getCarrierByName(carrierName);
    }

    public void registerParcelCarrier(ParcelCarrier parcelCarrier) {
        this.parcelCarrierManager.registerParcelCarrier(parcelCarrier);
    }

    public List<ParcelCarrier> getAllParcelCarriers() {
        return this.parcelCarrierManager.getAll();
    }

    public boolean isFormulaValid(String formula, List<String> variables) {
        return this.expressionSolver.isValid(formula, variables);
    }

    public List<User> getAllUsers() {
        return this.userManager.getAll();
    }

    public void validateUsername(String username) {
        this.userManager.validateUsername(username);
    }

    public User createUser(String username, String email, String name, String address, String taxNumber) {
        return this.userManager.createUser(username, email, name, address, taxNumber);
    }

    public User getUserByEmail(String email) {
        return this.userManager.getUserByEmail(email);
    }

    public boolean existsUserWithEmail(String email) {
        return this.userManager.existsUserWithEmail(email);
    }
}
