package com.marketplace.vintage.item;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

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
        Item testItem = createTestItem(id);
        String alphanumericID = testItem.getAlphanumericId();

        assertEquals(id, alphanumericID);

        itemManager.registerItem(testItem);
        Item testGetItem = itemManager.getItem(alphanumericID);

        assertEquals(alphanumericID, testGetItem.getAlphanumericId());

        assertThrowsExactly(EntityNotFoundException.class, () -> itemManager.getItem(itemManager.generateUniqueCode()));
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> itemManager.registerItem(testGetItem));
    }

    private Item createTestItem(String id) {
        return new Item(UUID.randomUUID(), id, NEW, "TEST", "BRAND", BigDecimal.valueOf(100), "DHL") {
            @Override
            public ItemType getItemType() {
                return null;
            }

            @Override
            public BigDecimal getPriceCorrection(int currentYear) {
                return new BigDecimal(0);
            }
        };
    }
}

