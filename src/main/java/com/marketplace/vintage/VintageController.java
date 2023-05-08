package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.*;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderFactory;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.VintageDate;

import java.util.List;
import java.util.Map;

public class VintageController {

    private final ItemManager itemManager;
    private final ItemFactory itemFactory;
    private final OrderManager orderManager;
    private final VintageTimeManager vintageTimeManager;
    private final ParcelCarrierManager parcelCarrierManager;
    private final ExpressionSolver expressionSolver;
    private final OrderFactory orderFactory;

    public VintageController(ItemManager itemManager, ItemFactory itemFactory, OrderManager orderManager, VintageTimeManager vintageTimeManager, ParcelCarrierManager parcelCarrierManager, ExpressionSolver expressionSolver, OrderFactory orderFactory) {
        this.itemManager = itemManager;
        this.itemFactory = itemFactory;
        this.orderManager = orderManager;
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
        this.expressionSolver = expressionSolver;
        this.orderFactory = orderFactory;
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

}
