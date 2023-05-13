package com.marketplace.vintage.item;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import com.marketplace.vintage.item.impl.MalaItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.marketplace.vintage.item.condition.ItemConditions.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class ItemManagerTest {

    @Test
    void testItemManager() {
        ItemManager itemManager = new ItemManager();
        String id = itemManager.generateUniqueCode();
        Item testItem = new MalaItem(
                UUID.randomUUID(),
                id,
                1,
                NEW,
                "description",
                "brand",
                BigDecimal.valueOf(100),
                null,
                1,
                "material",
                2021,
                1);

        String alphanumericID = testItem.getAlphanumericId();

        assertEquals(id, alphanumericID);

        itemManager.registerItem(testItem);
        Item testGetItem = itemManager.getItem(alphanumericID);

        assertEquals(alphanumericID, testGetItem.getAlphanumericId());

        assertThrowsExactly(EntityNotFoundException.class, () -> itemManager.getItem(itemManager.generateUniqueCode()));
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> itemManager.registerItem(testGetItem));
    }

}

