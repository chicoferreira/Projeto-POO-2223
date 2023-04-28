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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ItemFactoryTest {

    private ItemFactory itemFactory;

    @BeforeEach
    void setUp() {
        ItemManager itemManager = new ItemManager();
        this.itemFactory = new ItemFactory(itemManager);
    }

    @Test
    void testCreateMala() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful mala");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_UUID, UUID.randomUUID());
        itemProperties.put(ItemProperty.DIMENSION_AREA, 100);
        itemProperties.put(ItemProperty.MATERIAL, "Wood");
        itemProperties.put(ItemProperty.COLLECTION_YEAR, 2022);
        itemProperties.put(ItemProperty.APPRECIATION_RATE_OVER_YEARS, 5);

        MalaItem malaItem = (MalaItem) itemFactory.createItem(ItemType.MALA, itemProperties);

        assertEquals(NEW, malaItem.getItemCondition());
        assertEquals("A beautiful mala", malaItem.getDescription());
        assertEquals("MyBrand", malaItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), malaItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_UUID), malaItem.getParcelCarrierUuid());
        assertEquals(100, malaItem.getDimensionArea());
        assertEquals("Wood", malaItem.getMaterial());
        assertEquals(2022, malaItem.getCollectionYear());
        assertEquals(5, malaItem.getAppreciationRateOverYears());

    }

    @Test
    void testCreateSapatilhas() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful sapatilha");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_UUID, UUID.randomUUID());
        itemProperties.put(ItemProperty.SAPATILHA_SIZE, 100);
        itemProperties.put(ItemProperty.HAS_LACES, false);
        itemProperties.put(ItemProperty.COLOR, "blue");
        itemProperties.put(ItemProperty.COLLECTION_YEAR, 2022);

        SapatilhasItem sapatilhasItem = (SapatilhasItem) itemFactory.createItem(ItemType.SAPATILHAS, itemProperties);

        assertEquals(NEW, sapatilhasItem.getItemCondition());
        assertEquals("A beautiful sapatilha", sapatilhasItem.getDescription());
        assertEquals("MyBrand", sapatilhasItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), sapatilhasItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_UUID), sapatilhasItem.getParcelCarrierUuid());
        assertEquals(100, sapatilhasItem.getSize());
        assertFalse(sapatilhasItem.hasLaces());
        assertEquals("blue", sapatilhasItem.getColor());
        assertEquals(2022, sapatilhasItem.getCollectionYear());

    }

    @Test
    void testCreateTshirts() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful tshirt");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_UUID, UUID.randomUUID());
        itemProperties.put(ItemProperty.TSHIRT_SIZE, LARGE);
        itemProperties.put(ItemProperty.TSHIRT_PATTERN, STRIPES);

        TshirtItem tshirtItem = (TshirtItem) itemFactory.createItem(ItemType.TSHIRT, itemProperties);

        assertEquals(NEW, tshirtItem.getItemCondition());
        assertEquals("A beautiful tshirt", tshirtItem.getDescription());
        assertEquals("MyBrand", tshirtItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), tshirtItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_UUID), tshirtItem.getParcelCarrierUuid());
        assertEquals(LARGE, tshirtItem.getSize());
        assertEquals(STRIPES, tshirtItem.getPattern());

    }

    @Test
    void testCreateMalaPremium() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful mala");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_UUID, UUID.randomUUID());
        itemProperties.put(ItemProperty.DIMENSION_AREA, 100);
        itemProperties.put(ItemProperty.MATERIAL, "Wood");
        itemProperties.put(ItemProperty.COLLECTION_YEAR, 2022);
        itemProperties.put(ItemProperty.APPRECIATION_RATE_OVER_YEARS, 5);

        PremiumMalaItem malaItem = (PremiumMalaItem) itemFactory.createItem(ItemType.MALA_PREMIUM, itemProperties);

        assertEquals(NEW, malaItem.getItemCondition());
        assertEquals("A beautiful mala", malaItem.getDescription());
        assertEquals("MyBrand", malaItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), malaItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_UUID), malaItem.getParcelCarrierUuid());
        assertEquals(100, malaItem.getDimensionArea());
        assertEquals("Wood", malaItem.getMaterial());
        assertEquals(2022, malaItem.getCollectionYear());
        assertEquals(5, malaItem.getAppreciationRateOverYears());

    }

    @Test
    void testCreateSapatilhasPremium() {
        Map<ItemProperty, Object> itemProperties = new HashMap<>();
        itemProperties.put(ItemProperty.ITEM_CONDITION, NEW);
        itemProperties.put(ItemProperty.DESCRIPTION, "A beautiful sapatilha");
        itemProperties.put(ItemProperty.BRAND, "MyBrand");
        itemProperties.put(ItemProperty.BASE_PRICE, BigDecimal.valueOf(50.00));
        itemProperties.put(ItemProperty.PARCEL_CARRIER_UUID, UUID.randomUUID());
        itemProperties.put(ItemProperty.SAPATILHA_SIZE, 100);
        itemProperties.put(ItemProperty.HAS_LACES, false);
        itemProperties.put(ItemProperty.COLOR, "blue");
        itemProperties.put(ItemProperty.COLLECTION_YEAR, 2022);
        itemProperties.put(ItemProperty.APPRECIATION_RATE_OVER_YEARS, 5);

        PremiumSapatilhasItem sapatilhasItem = (PremiumSapatilhasItem) itemFactory.createItem(ItemType.SAPATILHAS_PREMIUM, itemProperties);

        assertEquals(NEW, sapatilhasItem.getItemCondition());
        assertEquals("A beautiful sapatilha", sapatilhasItem.getDescription());
        assertEquals("MyBrand", sapatilhasItem.getBrand());
        assertEquals(BigDecimal.valueOf(50.00), sapatilhasItem.getBasePrice());
        assertEquals(itemProperties.get(ItemProperty.PARCEL_CARRIER_UUID), sapatilhasItem.getParcelCarrierUuid());
        assertEquals(100, sapatilhasItem.getSize());
        assertFalse(sapatilhasItem.hasLaces());
        assertEquals("blue", sapatilhasItem.getColor());
        assertEquals(2022, sapatilhasItem.getCollectionYear());
        assertEquals(5, sapatilhasItem.getAppreciationRateOverYears());

    }

}
