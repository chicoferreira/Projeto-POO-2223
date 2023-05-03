package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemFactory;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.order.OrderedItem;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VintageController {

    private final ItemManager itemManager;
    private final ItemFactory itemFactory;
    private final OrderManager orderManager;
    private final VintageTimeManager vintageTimeManager;
    private final ParcelCarrierManager parcelCarrierManager;
    private final ExpressionSolver expressionSolver;

    public VintageController(ItemManager itemManager, ItemFactory itemFactory, OrderManager orderManager, VintageTimeManager vintageTimeManager, ParcelCarrierManager parcelCarrierManager, ExpressionSolver expressionSolver) {
        this.itemManager = itemManager;
        this.itemFactory = itemFactory;
        this.orderManager = orderManager;
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
        this.expressionSolver = expressionSolver;
    }

    public Item registerNewItem(User owner, ItemType itemType, Map<ItemProperty, Object> properties) {
        String uniqueCode = itemManager.generateUniqueCode();
        Item item = itemFactory.createItem(owner.getId(), uniqueCode, itemType, properties);
        itemManager.registerItem(item);

        owner.addItemBeingSold(item.getAlphanumericId());

        return item;
    }

    public Order createOrder(User user, List<Item> itemsToBuy) {
        Map<String, List<Item>> itemsByParcelCarrier = separateItemsByParcelCarrier(itemsToBuy);

        int currentYear = vintageTimeManager.getCurrentYear();

        List<OrderedItem> orderedItems = new ArrayList<>();
        Map<String, BigDecimal> parcelCarrierExpeditionPrices = new HashMap<>();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<String, List<Item>> entry : itemsByParcelCarrier.entrySet()) {
            String parcelCarrierName = entry.getKey();
            List<Item> items = entry.getValue();

            ParcelCarrier parcelCarrier = parcelCarrierManager.getCarrierByName(parcelCarrierName);

            BigDecimal expeditionBasePrice = parcelCarrier.getBaseValueForExpedition(items.size());
            BigDecimal expeditionTax = parcelCarrier.getExpeditionTax();

            BigDecimal parcelCarrierTotalPrice = expressionSolver.solve(parcelCarrier.getExpeditionPriceExpression(), Map.of(VintageConstants.EXPEDITION_PRICE_EXPRESSION_BASE_PRICE_VARIABLE, expeditionBasePrice,
                                                                                                                             VintageConstants.EXPEDITION_PRICE_EXPRESSION_TAX_VARIABLE, expeditionTax));
            parcelCarrierExpeditionPrices.put(parcelCarrierName, parcelCarrierTotalPrice);

            totalPrice = totalPrice.add(parcelCarrierTotalPrice);

            for (Item item : items) {
                BigDecimal itemPrice = item.getFinalPrice(currentYear);
                totalPrice = totalPrice.add(itemPrice);

                OrderedItem orderedItem = new OrderedItem(item.getAlphanumericId(), 1, item.getParcelCarrierName(), itemPrice);
                orderedItems.add(orderedItem);
            }
        }

        String orderId = this.orderManager.generateUniqueOrderId();
        Order order = new Order(orderId, user.getId(), orderedItems, parcelCarrierExpeditionPrices, totalPrice, vintageTimeManager.getCurrentDate());

        orderManager.registerOrder(order);
        user.getCompletedOrderIdsList().add(orderId);

        return order;
    }

    @NotNull
    private static Map<String, List<Item>> separateItemsByParcelCarrier(List<Item> itemsToBuy) {
        Map<String, List<Item>> itemsByParcelCarrier = new HashMap<>();
        for (Item item : itemsToBuy) {
            String parcelCarrierName = item.getParcelCarrierName();
            itemsByParcelCarrier.getOrDefault(parcelCarrierName, new ArrayList<>()).add(item);
        }
        return itemsByParcelCarrier;
    }

}
