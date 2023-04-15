package com.marketplace.vintage.item;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemManager {

    private final Map<UUID, Item> itemsById;

    public ItemManager() {
        this.itemsById = new HashMap<>();
    }

    public Item getItem(UUID id) {
        if (!itemsById.containsKey(id)) {
            throw new EntityNotFoundException("An item with the id" + id + " was not found");
        }

        return itemsById.get(id);
    }

    public void addItem(Item item) {
        UUID itemId = item.getItemUuid();

        if (!itemsById.containsKey(itemId)) {
            throw new EntityAlreadyExistsException("An item with that id already exists");
        }

        this.itemsById.put(itemId, item);
    }
}
