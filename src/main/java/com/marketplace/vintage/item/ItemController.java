package com.marketplace.vintage.item;

public class ItemController {

    private final ItemManager itemManager;

    public ItemController(ItemManager itemManager) {
        this.itemManager = itemManager;
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
}
