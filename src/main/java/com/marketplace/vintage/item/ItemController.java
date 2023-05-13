package com.marketplace.vintage.item;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemController {

    private final ItemManager itemManager;
    private final ItemFactory itemFactory;

    public ItemController(ItemManager itemManager, ItemFactory itemFactory) {
        this.itemManager = itemManager;
        this.itemFactory = itemFactory;
    }

    public int addItemStock(Item item, int stock) {
        if (stock < 0)
            throw new IllegalArgumentException("Stock change cannot be negative.");

        int newStock = item.getCurrentStock() + stock;
        item.setCurrentStock(newStock);

        this.itemManager.updateItem(item);

        return newStock;
    }

    public int removeItemStock(Item item, int stock) {
        if (stock < 0)
            throw new IllegalArgumentException("Stock change cannot be negative.");

        int newStock = Math.max(0, item.getCurrentStock() - stock);
        item.setCurrentStock(newStock);

        this.itemManager.updateItem(item);

        return newStock;
    }

    public boolean itemHasStock(String itemId) {
        Item item = this.itemManager.getItem(itemId);
        return item.getCurrentStock() > 0;
    }

    public Item getItem(String id) {
        return this.itemManager.getItem(id);
    }

    public void registerItem(Item item) {
        this.itemManager.registerItem(item);
    }

    public String generateUniqueCode() {
        return this.itemManager.generateUniqueCode();
    }

    public boolean containsItemById(String id) {
        return this.itemManager.containsItemById(id);
    }

    public List<Item> getAllItems() {
        return this.itemManager.getAllItems();
    }

    public Item createItem(UUID ownerUuid, String alphanumericId, ItemType itemType, Map<ItemProperty, Object> itemProperties) {
        return this.itemFactory.createItem(ownerUuid, alphanumericId, itemType, itemProperties);
    }

}
