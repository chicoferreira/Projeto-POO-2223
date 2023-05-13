package com.marketplace.vintage.item;

import com.marketplace.vintage.item.impl.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.marketplace.vintage.item.condition.ItemConditions.NEW;
import static com.marketplace.vintage.item.impl.TshirtItem.TshirtItemPattern.STRIPES;
import static com.marketplace.vintage.item.impl.TshirtItem.TshirtItemSize.LARGE;
import static org.junit.jupiter.api.Assertions.*;

public class ItemFactoryTest {

    private ItemFactory itemFactory;

    @BeforeEach
    void setUp() {
        this.itemFactory = new ItemFactory();
    }

    @Test
    void testCreateMala() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.STOCK, 1);
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful mala");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_NAME, "DHL");
        itemProperties.put(ItemProperty.DIMENSION_AREA, 100);
        itemProperties.put(ItemProperty.MATERIAL, "Wood");
        itemProperties.put(ItemProperty.COLLECTION_YEAR, 2022);
        itemProperties.put(ItemProperty.DEPRECIATION_RATE_OVER_YEARS, 5);

        UUID ownerUuid = UUID.randomUUID();
        String id = "TEST_ID";

        MalaItem malaItem = (MalaItem) itemFactory.createItem(ownerUuid, id, ItemType.MALA, itemProperties);

        assertEquals(ownerUuid, malaItem.getOwnerUuid());
        assertEquals(id, malaItem.getAlphanumericId());
        assertEquals(1, malaItem.getCurrentStock());
        assertEquals(NEW, malaItem.getItemCondition());
        assertEquals("A beautiful mala", malaItem.getDescription());
        assertEquals("MyBrand", malaItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), malaItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_NAME), malaItem.getParcelCarrierName());
        assertEquals(100, malaItem.getDimensionArea());
        assertEquals("Wood", malaItem.getMaterial());
        assertEquals(2022, malaItem.getCollectionYear());
        assertEquals(5, malaItem.getDepreciationRateOverYears());

        assertNotSame(malaItem.clone(), malaItem);
        assertEquals(malaItem.clone(), malaItem);
    }

    @Test
    void testCreateSapatilhas() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.STOCK, 1);
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful sapatilha");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_NAME, "DHL");
        itemProperties.put(ItemProperty.SAPATILHA_SIZE, 100);
        itemProperties.put(ItemProperty.HAS_LACES, false);
        itemProperties.put(ItemProperty.COLOR, "blue");
        itemProperties.put(ItemProperty.COLLECTION_YEAR, 2022);

        UUID ownerUuid = UUID.randomUUID();
        String id = "TEST_ID";

        SapatilhasItem sapatilhasItem = (SapatilhasItem) itemFactory.createItem(ownerUuid, id, ItemType.SAPATILHAS, itemProperties);

        assertEquals(1, sapatilhasItem.getCurrentStock());
        assertEquals(NEW, sapatilhasItem.getItemCondition());
        assertEquals("A beautiful sapatilha", sapatilhasItem.getDescription());
        assertEquals("MyBrand", sapatilhasItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), sapatilhasItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_NAME), sapatilhasItem.getParcelCarrierName());
        assertEquals(100, sapatilhasItem.getSize());
        assertFalse(sapatilhasItem.hasLaces());
        assertEquals("blue", sapatilhasItem.getColor());
        assertEquals(2022, sapatilhasItem.getCollectionYear());

        assertNotSame(sapatilhasItem.clone(), sapatilhasItem);
        assertEquals(sapatilhasItem.clone(), sapatilhasItem);
    }

    @Test
    void testCreateTshirts() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.STOCK, 1);
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful tshirt");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_NAME, "DHL");
        itemProperties.put(ItemProperty.TSHIRT_SIZE, LARGE);
        itemProperties.put(ItemProperty.TSHIRT_PATTERN, STRIPES);

        UUID ownerUuid = UUID.randomUUID();
        String id = "TEST_ID";

        TshirtItem tshirtItem = (TshirtItem) itemFactory.createItem(ownerUuid, id, ItemType.TSHIRT, itemProperties);

        assertEquals(1, tshirtItem.getCurrentStock());
        assertEquals(NEW, tshirtItem.getItemCondition());
        assertEquals("A beautiful tshirt", tshirtItem.getDescription());
        assertEquals("MyBrand", tshirtItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), tshirtItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_NAME), tshirtItem.getParcelCarrierName());
        assertEquals(LARGE, tshirtItem.getSize());
        assertEquals(STRIPES, tshirtItem.getPattern());

        assertNotSame(tshirtItem.clone(), tshirtItem);
        assertEquals(tshirtItem.clone(), tshirtItem);
    }

    @Test
    void testCreateMalaPremium() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.STOCK, 1);
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful mala");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_NAME, "DHL");
        itemProperties.put(ItemProperty.DIMENSION_AREA, 100);
        itemProperties.put(ItemProperty.MATERIAL, "Wood");
        itemProperties.put(ItemProperty.COLLECTION_YEAR, 2022);
        itemProperties.put(ItemProperty.APPRECIATION_RATE_OVER_YEARS, 5);

        UUID ownerUuid = UUID.randomUUID();
        String id = "TEST_ID";

        PremiumMalaItem malaItem = (PremiumMalaItem) itemFactory.createItem(ownerUuid, id, ItemType.MALA_PREMIUM, itemProperties);

        assertEquals(1, malaItem.getCurrentStock());
        assertEquals(NEW, malaItem.getItemCondition());
        assertEquals("A beautiful mala", malaItem.getDescription());
        assertEquals("MyBrand", malaItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), malaItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_NAME), malaItem.getParcelCarrierName());
        assertEquals(100, malaItem.getDimensionArea());
        assertEquals("Wood", malaItem.getMaterial());
        assertEquals(2022, malaItem.getCollectionYear());
        assertEquals(5, malaItem.getDepreciationRateOverYears());

        assertNotSame(malaItem.clone(), malaItem);
        assertEquals(malaItem.clone(), malaItem);
    }

    @Test
    void testCreateSapatilhasPremium() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.STOCK, 1);
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful sapatilha");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_NAME, "DHL");
        itemProperties.put(ItemProperty.SAPATILHA_SIZE, 100);
        itemProperties.put(ItemProperty.HAS_LACES, false);
        itemProperties.put(ItemProperty.COLOR, "blue");
        itemProperties.put(ItemProperty.COLLECTION_YEAR, 2022);
        itemProperties.put(ItemProperty.APPRECIATION_RATE_OVER_YEARS, 5);

        UUID ownerUuid = UUID.randomUUID();
        String id = "TEST_ID";

        PremiumSapatilhasItem sapatilhasItem = (PremiumSapatilhasItem) itemFactory.createItem(ownerUuid, id, ItemType.SAPATILHAS_PREMIUM, itemProperties);

        assertEquals(1, sapatilhasItem.getCurrentStock());
        assertEquals(NEW, sapatilhasItem.getItemCondition());
        assertEquals("A beautiful sapatilha", sapatilhasItem.getDescription());
        assertEquals("MyBrand", sapatilhasItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), sapatilhasItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_NAME), sapatilhasItem.getParcelCarrierName());
        assertEquals(100, sapatilhasItem.getSize());
        assertFalse(sapatilhasItem.hasLaces());
        assertEquals("blue", sapatilhasItem.getColor());
        assertEquals(2022, sapatilhasItem.getCollectionYear());
        assertEquals(5, sapatilhasItem.getAppreciationRateOverYears());

        assertNotSame(sapatilhasItem.clone(), sapatilhasItem);
        assertEquals(sapatilhasItem.clone(), sapatilhasItem);
    }
}
