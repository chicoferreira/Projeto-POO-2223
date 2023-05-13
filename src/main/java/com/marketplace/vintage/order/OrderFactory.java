package com.marketplace.vintage.order;

import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.condition.ItemCondition;
import com.marketplace.vintage.utils.VintageDate;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class OrderFactory {

    public Order buildOrder(String orderId, UUID userId, List<Item> itemsToBuy, Function<String, ParcelCarrier> parcelCarrierSupplier, Supplier<VintageDate> currentDateSupplier, ExpressionSolver expressionSolver) {
        Map<String, List<Item>> itemsByParcelCarrier = separateItemsByParcelCarrier(itemsToBuy);

        VintageDate currentDate = currentDateSupplier.get();
        int currentYear = currentDate.getYear();

        OrderBuilder orderBuilder = OrderBuilder.newBuilder(orderId, userId, currentDate);

        for (Map.Entry<String, List<Item>> entry : itemsByParcelCarrier.entrySet()) {
            String parcelCarrierName = entry.getKey();
            List<Item> items = entry.getValue();

            ParcelCarrier parcelCarrier = parcelCarrierSupplier.apply(parcelCarrierName);

            for (Item item : items) {
                orderBuilder.addOrderedItem(item, currentYear);
                orderBuilder.addItemSatisfactionPrice(item, getSatisfactionPrice(item.getItemCondition()));
            }

            orderBuilder.addParcelCarrierShipmentCost(parcelCarrier.getName(), items.size(), parcelCarrier.getShippingCost(expressionSolver, items.size()));
        }

        return orderBuilder.build();
    }

    private BigDecimal getSatisfactionPrice(ItemCondition itemCondition) {
        return switch (itemCondition.getType()) {
            case NEW -> BigDecimal.valueOf(0.5);
            case USED -> BigDecimal.valueOf(0.25);
        };
    }

    public Map<String, List<Item>> separateItemsByParcelCarrier(List<Item> itemsToBuy) {
        Map<String, List<Item>> itemsByParcelCarrier = new HashMap<>();
        for (Item item : itemsToBuy) {
            String parcelCarrierName = item.getParcelCarrierName();
            List<Item> items = itemsByParcelCarrier.computeIfAbsent(parcelCarrierName, k -> new ArrayList<>());
            items.add(item);
        }
        return itemsByParcelCarrier;
    }


}
