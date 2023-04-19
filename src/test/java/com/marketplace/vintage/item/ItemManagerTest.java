package com.marketplace.vintage.item;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.item.condition.ItemCondition;
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
        Item testItem = createTestItem();
        UUID testItemID = testItem.getItemUuid();

        itemManager.addItem(testItem);
        Item testGetItem = itemManager.getItem(testItemID);

        assertEquals(testItemID, testGetItem.getItemUuid());

        assertThrowsExactly(EntityNotFoundException.class, () -> itemManager.getItem(UUID.randomUUID()));
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> itemManager.addItem(testGetItem));
    }

    private Item createTestItem() {
        ItemCondition testCondition = NEW;
        String testDescription = "TEST";
        String testBrand = "BRAND";
        String testNumericCode = "1";
        BigDecimal testBasePrice = BigDecimal.valueOf(100);
        UUID testCarrier = UUID.randomUUID();

        return new Item(testCondition, testDescription, testBrand, testNumericCode, testBasePrice, testCarrier) {
            @Override
            public BigDecimal getPriceCorrection(int currentYear) {
                return new BigDecimal(0);
            }
        };
    }
}

