package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.*;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.logging.PrefixLogger;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderController;
import com.marketplace.vintage.order.OrderFactory;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.scripting.ScriptController;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.utils.VintageDate;
import org.jetbrains.annotations.Nullable;

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
    private final ScriptController scriptController;

    public VintageController(ItemManager itemManager,
                             ItemFactory itemFactory,
                             OrderManager orderManager,
                             OrderController orderController,
                             VintageTimeManager vintageTimeManager,
                             ParcelCarrierManager parcelCarrierManager,
                             ExpressionSolver expressionSolver,
                             OrderFactory orderFactory,
                             UserManager userManager, ScriptController scriptController) {
        this.itemManager = itemManager;
        this.itemFactory = itemFactory;
        this.orderManager = orderManager;
        this.orderController = orderController;
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
        this.expressionSolver = expressionSolver;
        this.orderFactory = orderFactory;
        this.userManager = userManager;
        this.scriptController = scriptController;
    }

    public Item registerNewItem(User owner, ItemType itemType, Map<ItemProperty, Object> properties) {
        return registerNewItem(itemManager.generateUniqueCode(), owner, itemType, properties);
    }

    public Item registerNewItem(@Nullable String uniqueCode, User owner, ItemType itemType, Map<ItemProperty, Object> properties) {
        if (uniqueCode == null) return registerNewItem(owner, itemType, properties);

        Item item = itemFactory.createItem(owner.getId(), uniqueCode, itemType, properties);
        itemManager.registerItem(item);

        owner.addItemBeingSold(item.getAlphanumericId());

        return item;
    }

    public Order assembleOrder(User user) {
        return assembleOrder(orderManager.generateUniqueOrderId(), user);
    }

    public Order assembleOrder(@Nullable String orderId, User user) {
        if (orderId == null) {
            return assembleOrder(user);
        }

        List<String> shoppingCart = user.getShoppingCart();
        if (shoppingCart.isEmpty()) {
            throw new IllegalStateException("The shopping cart is empty.");
        }

        List<Item> itemList = shoppingCart.stream().map(itemManager::getItem).toList();
        if (itemList.stream().anyMatch(item -> item.getOwnerUuid().equals(user.getId()))) {
            throw new IllegalStateException("The shopping cart contains items that are owned by the user.");
        }

        return orderFactory.buildOrder(orderId, user.getId(), itemList);
    }

    public void registerOrder(Order order, User user) {
        orderManager.registerOrder(order);
        user.addCompletedOrderId(order.getOrderId());

        user.cleanShoppingCart();
    }

    public VintageDate getCurrentDate() {
        return this.vintageTimeManager.getCurrentDate();
    }

    /**
     * @param days number of days to jump
     * @return the new date
     */
    public VintageDate jumpTime(Logger logger, int days) {
        for (int i = 1; i <= days; i++) {
            VintageDate previousDate = this.vintageTimeManager.getCurrentDate();
            VintageDate newDate = this.vintageTimeManager.advanceTime(1);

            Logger dateLogger = PrefixLogger.of(newDate.toString(), logger);
            this.orderController.notifyTimeChange(dateLogger, newDate);
            this.scriptController.notifyTimeChange(dateLogger, previousDate, newDate);
        }

        return this.vintageTimeManager.getCurrentDate();
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

    public boolean isOrderReturnable(Order order) {
        return this.orderController.isOrderReturnable(order, this.vintageTimeManager.getCurrentDate());
    }

    public void returnOrder(Order order) {
        this.orderController.returnOrder(order);
    }
}
