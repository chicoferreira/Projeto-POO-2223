package com.marketplace.vintage.item;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.utils.AlphanumericGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemManager {

    private final Map<String, Item> itemsById;

    public ItemManager() {
        this.itemsById = new HashMap<>();
    }

    public Item getItem(String id) {
        if (!this.itemsById.containsKey(id)) {
            throw new EntityNotFoundException("An item with the id " + id + " was not found");
        }

        return this.itemsById.get(id);
    }

    public void addItem(Item item) {
        String itemId = item.getAlphanumericCode();

        if (itemsById.containsKey(itemId)) {
            throw new EntityAlreadyExistsException("An item with that id already exists");
        }

        this.itemsById.put(itemId, item);
    }

    public String generateUniqueID(Item item) {

        String uniqueID = AlphanumericGenerator.generateAlphanumericID("XXX-XXX");
        while (this.itemsById.containsKey(uniqueID)) uniqueID = AlphanumericGenerator.generateAlphanumericID("XXX-XXX");

        return uniqueID;

    }
}
