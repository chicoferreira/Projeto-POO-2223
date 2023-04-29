package com.marketplace.vintage;

import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemFactory;
import com.marketplace.vintage.item.ItemManager;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.user.User;

import java.util.Map;

public class VintageController {

    private final ItemManager itemManager;
    private final ItemFactory itemFactory;

    public VintageController(ItemManager itemManager, ItemFactory itemFactory) {
        this.itemManager = itemManager;
        this.itemFactory = itemFactory;
    }

    public Item registerNewItem(User owner, ItemType itemType, Map<ItemProperty, Object> properties) {
        String uniqueCode = itemManager.generateUniqueCode();
        Item item = itemFactory.createItem(owner.getId(), uniqueCode, itemType, properties);
        itemManager.registerItem(item);

        owner.addItemBeingSold(item.getAlphanumericId());

        return item;
    }
}
