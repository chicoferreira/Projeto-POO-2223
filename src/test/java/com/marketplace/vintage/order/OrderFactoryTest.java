package com.marketplace.vintage.order;

import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrierFactory;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.item.condition.ItemConditions;

import com.marketplace.vintage.utils.VintageDate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderFactoryTest {



    @Test
    void buildOrder() {
        VintageTimeManager vintageTimeManager = new VintageTimeManager(VintageDate.of(1, 1, 2023));
        ParcelCarrierManager parcelCarrierManager = new ParcelCarrierManager();
        parcelCarrierManager.registerParcelCarrier(ParcelCarrierFactory.createNormalParcelCarrier("DHL"));

        ExpressionSolver expressionSolver = Mockito.mock(ExpressionSolver.class);
        Mockito.when(expressionSolver.solve(Mockito.anyString(), Mockito.anyMap())).thenReturn(BigDecimal.valueOf(10));

        Item item = Mockito.mock(Item.class);
        Mockito.when(item.getAlphanumericId()).thenReturn("TEST-Item");
        Mockito.when(item.getOwnerUuid()).thenReturn(UUID.randomUUID());
        Mockito.when(item.getItemType()).thenReturn(ItemType.MALA);
        Mockito.when(item.getFinalPrice(Mockito.anyInt())).thenReturn(BigDecimal.valueOf(10));
        Mockito.when(item.getParcelCarrierName()).thenReturn("DHL");
        Mockito.when(item.getItemCondition()).thenReturn(ItemConditions.NEW);

        OrderFactory orderFactory = new OrderFactory(vintageTimeManager, parcelCarrierManager, expressionSolver);
        Order order = orderFactory.buildOrder("TEST-Order", UUID.randomUUID(), List.of(item));

        assertEquals("TEST-Order", order.getOrderId());
        assertEquals(1, order.getOrderedItems().size());

        OrderedItem orderedItem = order.getOrderedItems().get(0);
        assertEquals("TEST-Item", orderedItem.getItemId());
        assertEquals(BigDecimal.valueOf(10), orderedItem.getTotalPrice());
        assertEquals("DHL", orderedItem.getParcelCarrierName());
        assertEquals(order.getOrderedItemsByParcelCarrier("DHL").get(0), orderedItem);
    }

    @Test
    void separateItemsByParcelCarrier() {
        VintageTimeManager vintageTimeManager = new VintageTimeManager(VintageDate.of(1, 1, 2023));
        ParcelCarrierManager parcelCarrierManager = new ParcelCarrierManager();
        parcelCarrierManager.registerParcelCarrier(ParcelCarrierFactory.createNormalParcelCarrier("DHL"));

        ExpressionSolver expressionSolver = Mockito.mock(ExpressionSolver.class);

        Item item = Mockito.mock(Item.class);
        Mockito.when(item.getParcelCarrierName()).thenReturn("DHL");

        OrderFactory orderFactory = new OrderFactory(vintageTimeManager, parcelCarrierManager, expressionSolver);
        Map<String, List<Item>> itemsByParcelCarrier = orderFactory.separateItemsByParcelCarrier(List.of(item));

        assertEquals(1, itemsByParcelCarrier.size());
        assertEquals(1, itemsByParcelCarrier.get("DHL").size());
        assertEquals(item, itemsByParcelCarrier.get("DHL").get(0));
    }
}