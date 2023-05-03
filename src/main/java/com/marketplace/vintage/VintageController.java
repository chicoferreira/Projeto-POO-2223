package com.marketplace.vintage;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemFactory;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.order.ItemOrder;
import com.marketplace.vintage.order.Order;
import com.marketplace.vintage.order.OrderManager;
import com.marketplace.vintage.user.User;

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

    public VintageController(ItemManager itemManager, ItemFactory itemFactory, OrderManager orderManager, VintageTimeManager vintageTimeManager, ParcelCarrierManager parcelCarrierManager) {
        this.itemManager = itemManager;
        this.itemFactory = itemFactory;
        this.orderManager = orderManager;
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
    }

    public Item registerNewItem(User owner, ItemType itemType, Map<ItemProperty, Object> properties) {
        String uniqueCode = itemManager.generateUniqueCode();
        Item item = itemFactory.createItem(owner.getId(), uniqueCode, itemType, properties);
        itemManager.registerItem(item);

        owner.addItemBeingSold(item.getAlphanumericId());

        return item;
    }

    public Order createOrder(User user, List<Item> itemsToBuy) {
        Map<String, Integer> parcelCarrierToNumberOfItems = new HashMap<>();
        for (Item item : itemsToBuy) {
            String parcelCarrierName = item.getParcelCarrierName();
            int numberOfItems = parcelCarrierToNumberOfItems.getOrDefault(parcelCarrierName, 0);
            parcelCarrierToNumberOfItems.put(parcelCarrierName, numberOfItems + 1);
        }

        List<ItemOrder> itemOrders = new ArrayList<>();
        for (Item item : itemsToBuy) {
            String parcelCarrierName = item.getParcelCarrierName();
            int numberOfItems = parcelCarrierToNumberOfItems.get(parcelCarrierName); // TODO: multiply price by number of items
            BigDecimal price = item.getFinalPrice(vintageTimeManager.getCurrentYear());
            ItemOrder itemOrder = new ItemOrder(item.getAlphanumericId(), 0, parcelCarrierName, price);
            itemOrders.add(itemOrder);
        }

        Map<String, BigDecimal> parcelCarrierToPrice = new HashMap<>();

        for (Map.Entry<String, Integer> entry : parcelCarrierToNumberOfItems.entrySet()) {
            String parcelCarrierName = entry.getKey();
            int numberOfItems = entry.getValue();
            ParcelCarrier parcelCarrier = parcelCarrierManager.getCarrierByName(parcelCarrierName);

            BigDecimal price = parcelCarrierManager.getPrice(parcelCarrierName, numberOfItems);
            parcelCarrierToPrice.put(parcelCarrierName, price);
        }

        String orderId = this.orderManager.generateUniqueOrderId();
        Order order = new Order(orderId, user.getId(), itemOrders, parcelCarrierToPrice, );

        return order;
    }
}
