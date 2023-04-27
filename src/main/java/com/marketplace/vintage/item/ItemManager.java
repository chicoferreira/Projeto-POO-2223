package com.marketplace.vintage.item;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.utils.AlphanumericGenerator;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private final Map<String, Item> itemsById;

    private static final String ITEM_ID_FORMAT = "XXX-XXX";

    public ItemManager() {
        this.itemsById = new HashMap<>();
    }

    public Item getItem(String id) {
        if (!this.itemsById.containsKey(id)) {
            throw new EntityNotFoundException("An item with the id " + id + " was not found");
        }

        return this.itemsById.get(id);
    }

    public void registerItem(Item item) {
        String itemId = item.getAlphanumericID();

        if (itemsById.containsKey(itemId)) {
            throw new EntityAlreadyExistsException("An item with that id already exists");
        }

        this.itemsById.put(itemId, item);
    }

    public String generateUniqueCode() {
        String uniqueCode = AlphanumericGenerator.generateAlphanumericCode(ITEM_ID_FORMAT);
        while (this.itemsById.containsKey(uniqueCode)) {
            uniqueCode = AlphanumericGenerator.generateAlphanumericCode(ITEM_ID_FORMAT);
        }

        return uniqueCode;
    }
}
