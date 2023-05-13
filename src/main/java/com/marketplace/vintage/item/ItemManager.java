package com.marketplace.vintage.item;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.utils.AlphanumericGenerator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager implements Serializable {

    private final Map<String, Item> itemsById;

    private static final String ITEM_ID_FORMAT = "XXX-XXX";

    public ItemManager() {
        this.itemsById = new HashMap<>();
    }

    public Item getItem(String id) {
        if (!this.itemsById.containsKey(id)) {
            throw new EntityNotFoundException("An item with the id " + id + " was not found");
        }

        return this.itemsById.get(id).clone();
    }

    public void registerItem(Item item) {
        String itemId = item.getAlphanumericId();

        if (AlphanumericGenerator.isOfFormat(ITEM_ID_FORMAT, itemId)) {
            throw new IllegalArgumentException("The item id must be of the format " + ITEM_ID_FORMAT);
        }

        if (itemsById.containsKey(itemId)) {
            throw new EntityAlreadyExistsException("An item with that id already exists");
        }

        this.itemsById.put(itemId, item);
    }

    public void updateItem(Item item) {
        String alphanumericId = item.getAlphanumericId();

        if (!itemsById.containsKey(alphanumericId)) {
            throw new EntityNotFoundException("An item with the id " + alphanumericId + " was not found");
        }

        itemsById.put(alphanumericId, item);
    }

    public String generateUniqueCode() {
        String uniqueCode = AlphanumericGenerator.generateAlphanumericCode(ITEM_ID_FORMAT);
        if (containsItemById(uniqueCode)) {
            return generateUniqueCode();
        }
        return uniqueCode;
    }

    public boolean containsItemById(String id) {
        return itemsById.containsKey(id);
    }

    public List<Item> getAllItems() {
        return List.copyOf(itemsById.values());
    }
}
