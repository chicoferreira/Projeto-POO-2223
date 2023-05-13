package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierController;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.*;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.logging.PrefixLogger;
import com.marketplace.vintage.order.*;
import com.marketplace.vintage.scripting.ScriptController;
import com.marketplace.vintage.stats.StatsManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserController;
import com.marketplace.vintage.user.UserManager;
import com.marketplace.vintage.utils.VintageDate;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class VintageController {

    private final ItemManager itemManager;
    private final ItemFactory itemFactory;
    private final ItemController itemController;
    private final OrderManager orderManager;
    private final OrderController orderController;
    private final VintageTimeManager vintageTimeManager;
    private final ParcelCarrierManager parcelCarrierManager;
    private final ParcelCarrierController parcelCarrierController;
    private final ExpressionSolver expressionSolver;
    private final OrderFactory orderFactory;
    private final UserManager userManager;
    private final UserController userController;
    private final ScriptController scriptController;
    private final StatsManager statsManager;

    public VintageController(ItemManager itemManager,
                             ItemFactory itemFactory,
                             ItemController itemController,
                             OrderManager orderManager,
                             OrderController orderController,
                             VintageTimeManager vintageTimeManager,
                             ParcelCarrierManager parcelCarrierManager,
                             ParcelCarrierController parcelCarrierController,
                             ExpressionSolver expressionSolver,
                             OrderFactory orderFactory,
                             UserManager userManager,
                             UserController userController,
                             ScriptController scriptController,
                             StatsManager statsManager) {
        this.itemManager = itemManager;
        this.itemFactory = itemFactory;
        this.itemController = itemController;
        this.orderManager = orderManager;
        this.orderController = orderController;
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
        this.parcelCarrierController = parcelCarrierController;
        this.expressionSolver = expressionSolver;
        this.orderFactory = orderFactory;
        this.userManager = userManager;
        this.userController = userController;
        this.scriptController = scriptController;
        this.statsManager = statsManager;
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

        for (Item item : itemList) {
            if (item.getCurrentStock() <= 0) {
                throw new IllegalStateException("The shopping cart contains items that are out of stock.");
            }
        }

        return orderFactory.buildOrder(orderId, user.getId(), itemList);
    }

    public void registerOrder(Order order, User user) {
        orderManager.registerOrder(order);
        userController.addCompletedOrderId(user, order.getOrderId());

        for (OrderedItem orderedItem : order.getOrderedItems()) {
            UUID sellerId = orderedItem.getSellerId();
            User seller = userManager.getUserById(sellerId);
            this.userController.addCompletedSellOrderId(seller, order.getOrderId());

            String parcelCarrierName = orderedItem.getParcelCarrierName();

            ParcelCarrier parcelCarrier = parcelCarrierManager.getCarrierByName(parcelCarrierName);
            this.parcelCarrierController.addDeliveredOrderId(parcelCarrier, order.getOrderId());

            String itemId = orderedItem.getItemId();
            Item item = itemManager.getItem(itemId);

            this.itemController.removeItemStock(item, 1);
        }

        userController.cleanShoppingCart(user);
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

    public boolean existsItem(String itemId) {
        return this.itemManager.containsItemById(itemId);
    }

    public Order getOrder(String orderId) {
        return this.orderManager.getOrder(orderId);
    }

    public boolean existsOrder(String id) {
        return this.orderManager.containsOrder(id);
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

    public void setCarrierExpeditionPriceExpression(ParcelCarrier carrier, String formula) {
        this.parcelCarrierController.setCarrierExpeditionPriceExpression(carrier, formula);
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

    public User getUserByUsername(String username) {
        return this.userManager.getUserByUsername(username);
    }

    public User getUserById(UUID id) {
        return this.userManager.getUserById(id);
    }

    public boolean existsUserWithUsername(String username) {
        return this.userManager.existsUserWithUsername(username);
    }

    public boolean existsUserWithEmail(String email) {
        return this.userManager.existsUserWithEmail(email);
    }

    public void addItemToShoppingCart(User user, String itemId) {
        this.userController.addItemToShoppingCart(user, itemId);
    }

    public void removeItemFromShoppingCart(User user, String itemId) {
        this.userController.removeItemFromShoppingCart(user, itemId);
    }

    public boolean isOrderReturnable(Order order) {
        return this.orderController.isOrderReturnable(order, this.vintageTimeManager.getCurrentDate());
    }

    public void returnOrder(Order order) {
        this.orderController.returnOrder(order);

        for (OrderedItem orderedItem : order.getOrderedItems()) {
            String itemId = orderedItem.getItemId();
            Item item = this.itemManager.getItem(itemId);

            addItemStock(item, 1);
        }
    }

    public User getBestSeller(Predicate<VintageDate> datePredicate) {
        return this.statsManager.getSellerWithMoreMoneySales(datePredicate);
    }

    public ParcelCarrier getParcelCarrierWithMoreMoneyReceived() {
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

    public BigDecimal getMoneySpentInDatePredicate(User user, Predicate<VintageDate> datePredicate) {
        return this.statsManager.getMoneySpentInDatePredicate(user, datePredicate);
    }

    public BigDecimal getMoneyFromSalesByDatePredicate(User user, Predicate<VintageDate> datePredicate) {
        return this.statsManager.getMoneyFromSalesByDatePredicate(user, datePredicate);
    }

    public BigDecimal getParcelCarrierReceivedMoney(ParcelCarrier parcelCarrier) {
        return this.statsManager.getParcelCarrierReceivedMoney(parcelCarrier);
    }

    public List<Item> getAllItems() {
        return this.itemManager.getAllItems();
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
