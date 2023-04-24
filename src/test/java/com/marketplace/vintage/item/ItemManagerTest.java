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
        String id = itemManager.generateUniqueID("XXX-XXX");
        Item testItem = createTestItem(id);
        String alphanumericID = testItem.getAlphanumericID();

        assertEquals(id,alphanumericID);

        itemManager.addItem(testItem);
        Item testGetItem = itemManager.getItem(alphanumericID);

        assertEquals(alphanumericID, testGetItem.getAlphanumericID());

        String secondAlphanumericID = itemManager.generateUniqueID("XXX-XXX");
        final String finalTestID = secondAlphanumericID;

        assertThrowsExactly(EntityNotFoundException.class, () -> itemManager.getItem(finalTestID));
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> itemManager.addItem(testGetItem));
    }

    private Item createTestItem(String id) {
        String alphaNumericID = id;
        String testDescription = "TEST";
        String testBrand = "BRAND";
        BigDecimal testBasePrice = BigDecimal.valueOf(100);
        UUID testCarrier = UUID.randomUUID();

        return new Item(alphaNumericID, NEW, testDescription, testBrand, testBasePrice, testCarrier) {
            @Override
            public BigDecimal getPriceCorrection(int currentYear) {
                return new BigDecimal(0);
            }
        };
    }
}

