package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierController;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.*;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.logging.PrefixLogger;
import com.marketplace.vintage.order.*;
import com.marketplace.vintage.scripting.ScriptController;
import com.marketplace.vintage.stats.StatsManager;
import com.marketplace.vintage.time.TimeManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserController;
import com.marketplace.vintage.utils.VintageDate;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class Vintage {

    private final ItemController itemController;
    private final OrderController orderController;
    private final TimeManager timeManager;
    private final ParcelCarrierController parcelCarrierController;
    private final ExpressionSolver expressionSolver;
    private final UserController userController;
    private final ScriptController scriptController;
    private final StatsManager statsManager;

    public Vintage(ItemController itemController,
                   OrderController orderController,
                   TimeManager timeManager,
                   ParcelCarrierController parcelCarrierController,
                   ExpressionSolver expressionSolver,
                   UserController userController,
                   ScriptController scriptController) {
        this.itemController = itemController;
        this.orderController = orderController;
        this.timeManager = timeManager;
        this.parcelCarrierController = parcelCarrierController;
        this.expressionSolver = expressionSolver;
        this.userController = userController;
        this.scriptController = scriptController;
        this.statsManager = new StatsManager(this);
    }

    public Item registerNewItem(User owner, ItemType itemType, Map<ItemProperty, Object> properties) {
        return registerNewItem(itemController.generateUniqueCode(), owner, itemType, properties);
    }

    public Item registerNewItem(@Nullable String uniqueCode, User owner, ItemType itemType, Map<ItemProperty, Object> properties) {
        if (uniqueCode == null) return registerNewItem(owner, itemType, properties);

        Item item = itemController.createItem(owner.getId(), uniqueCode, itemType, properties);
        itemController.registerItem(item);

        owner.addItemBeingSold(item.getAlphanumericId());

        return item;
    }

    public Order assembleOrder(User user) {
        return assembleOrder(orderController.generateUniqueOrderId(), user);
    }

    public Order assembleOrder(@Nullable String orderId, User user) {
        if (orderId == null) {
            return assembleOrder(user);
        }

        List<String> shoppingCart = user.getShoppingCart();
        if (shoppingCart.isEmpty()) {
            throw new IllegalStateException("The shopping cart is empty.");
        }

        List<Item> itemList = shoppingCart.stream().map(itemController::getItem).toList();
        if (itemList.stream().anyMatch(item -> item.getOwnerUuid().equals(user.getId()))) {
            throw new IllegalStateException("The shopping cart contains items that are owned by the user.");
        }

        for (Item item : itemList) {
            if (item.getCurrentStock() <= 0) {
                throw new IllegalStateException("The shopping cart contains items that are out of stock.");
            }
        }

        return orderController.buildOrder(orderId, user.getId(), itemList, this.parcelCarrierController::getCarrierByName, this.timeManager::getCurrentDate, expressionSolver);
    }

    public void registerOrder(Order order, User user) {
        orderController.registerOrder(order);
        userController.addCompletedOrderId(user, order.getOrderId());

        for (OrderedItem orderedItem : order.getOrderedItems()) {
            UUID sellerId = orderedItem.getSellerId();
            User seller = userController.getUserById(sellerId);
            this.userController.addCompletedSellOrderId(seller, order.getOrderId());

            String parcelCarrierName = orderedItem.getParcelCarrierName();

            ParcelCarrier parcelCarrier = parcelCarrierController.getCarrierByName(parcelCarrierName);
            this.parcelCarrierController.addDeliveredOrderId(parcelCarrier, order.getOrderId());

            String itemId = orderedItem.getItemId();
            Item item = itemController.getItem(itemId);

            this.itemController.removeItemStock(item, 1);
        }

        userController.cleanShoppingCart(user);
    }

    public VintageDate getCurrentDate() {
        return this.timeManager.getCurrentDate();
    }

    /**
     * @param days number of days to jump
     * @return the new date
     */
    public VintageDate jumpTime(Logger logger, int days) {
        for (int i = 1; i <= days; i++) {
            VintageDate previousDate = this.timeManager.getCurrentDate();
            VintageDate newDate = this.timeManager.advanceTime(1);

            Logger dateLogger = PrefixLogger.of(newDate.toString(), logger);
            this.orderController.notifyTimeChange(dateLogger, newDate);
            this.scriptController.notifyTimeChange(dateLogger, previousDate, newDate);
        }

        return this.timeManager.getCurrentDate();
    }

    public int getCurrentYear() {
        return this.timeManager.getCurrentYear();
    }

    public boolean containsItemById(String itemId) {
        return this.itemController.containsItemById(itemId);
    }

    public Item getItem(String itemId) {
        return this.itemController.getItem(itemId);
    }

    public boolean existsItem(String itemId) {
        return this.itemController.containsItemById(itemId);
    }

    public Order getOrder(String orderId) {
        return this.orderController.getOrder(orderId);
    }

    public boolean existsOrder(String id) {
        return this.orderController.containsOrder(id);
    }

    public List<Order> getAllOrders() {
        return this.orderController.getAll();
    }

    public List<ParcelCarrier> getAllParcelCarriersCompatibleWith(ItemType itemType) {
        return this.parcelCarrierController.getAllCompatibleWith(itemType);
    }

    public boolean containsCarrierByName(String carrierName) {
        return this.parcelCarrierController.containsCarrierByName(carrierName);
    }

    public ParcelCarrier getCarrierByName(String carrierName) {
        return this.parcelCarrierController.getCarrierByName(carrierName);
    }

    public void registerParcelCarrier(ParcelCarrier parcelCarrier) {
        this.parcelCarrierController.registerParcelCarrier(parcelCarrier);
    }

    public List<ParcelCarrier> getAllParcelCarriers() {
        return this.parcelCarrierController.getAll();
    }

    public void setCarrierExpeditionPriceExpression(ParcelCarrier carrier, String formula) {
        this.parcelCarrierController.setCarrierExpeditionPriceExpression(carrier, formula);
    }

    public boolean isFormulaValid(String formula, List<String> variables) {
        return this.expressionSolver.isValid(formula, variables);
    }

    public List<User> getAllUsers() {
        return this.userController.getAll();
    }

    public void validateUsername(String username) {
        this.userController.validateUsername(username);
    }

    public User createUser(String username, String email, String name, String address, String taxNumber) {
        return this.userController.createUser(username, email, name, address, taxNumber);
    }

    public User getUserByEmail(String email) {
        return this.userController.getUserByEmail(email);
    }

    public User getUserByUsername(String username) {
        return this.userController.getUserByUsername(username);
    }

    public User getUserById(UUID id) {
        return this.userController.getUserById(id);
    }

    public boolean existsUserWithUsername(String username) {
        return this.userController.existsUserWithUsername(username);
    }

    public boolean existsUserWithEmail(String email) {
        return this.userController.existsUserWithEmail(email);
    }

    public void addItemToShoppingCart(User user, String itemId) {
        this.userController.addItemToShoppingCart(user, itemId);
    }

    public void removeItemFromShoppingCart(User user, String itemId) {
        this.userController.removeItemFromShoppingCart(user, itemId);
    }

    public boolean isOrderReturnable(Order order) {
        return this.orderController.isOrderReturnable(order, this.timeManager.getCurrentDate());
    }

    public void returnOrder(Order order) {
        this.orderController.returnOrder(order);

        for (OrderedItem orderedItem : order.getOrderedItems()) {
            String itemId = orderedItem.getItemId();
            Item item = this.itemController.getItem(itemId);

            addItemStock(item, 1);
        }
    }

    public @Nullable User getBestSeller(Predicate<VintageDate> datePredicate) {
        return this.statsManager.getSellerWithMoreMoneySales(datePredicate);
    }

    public @Nullable ParcelCarrier getParcelCarrierWithMoreMoneyReceived() {
        return this.statsManager.getParcelCarrierWithMoreMoneyReceived();
    }

    public List<User> getTopBuyers(int limit, Predicate<VintageDate> datePredicate) {
        return this.statsManager.getTopBuyers(limit, datePredicate);
    }

    public List<User> getTopSellers(int limit, Predicate<VintageDate> datePredicate) {
        return this.statsManager.getTopSellers(limit, datePredicate);
    }

    public BigDecimal getVintageProfit() {
        return this.statsManager.getVintageProfit();
    }

    public BigDecimal getMoneySpentByUserInDatePredicate(User user, Predicate<VintageDate> datePredicate) {
        return this.statsManager.getMoneySpentByUserInDatePredicate(user, datePredicate);
    }

    public BigDecimal getMoneyFromUserSalesByDatePredicate(User user, Predicate<VintageDate> datePredicate) {
        return this.statsManager.getMoneyFromUserSalesByDatePredicate(user, datePredicate);
    }

    public BigDecimal getParcelCarrierReceivedMoney(ParcelCarrier parcelCarrier) {
        return this.statsManager.getParcelCarrierReceivedMoney(parcelCarrier);
    }

    public List<Item> getAllItems() {
        return this.itemController.getAllItems();
    }

    public int addItemStock(Item item, int stock) {
        return this.itemController.addItemStock(item, stock);
    }

    public int removeItemStock(Item item, int stock) {
        return this.itemController.removeItemStock(item, stock);
    }

    public boolean itemHasStock(String itemId) {
        return this.itemController.itemHasStock(itemId);
    }
}
