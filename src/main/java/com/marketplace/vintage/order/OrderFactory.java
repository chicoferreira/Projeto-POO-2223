package com.marketplace.vintage.order;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.*;

public class OrderFactory {

    private final VintageTimeManager vintageTimeManager;
    private final ParcelCarrierManager parcelCarrierManager;
    private final ExpressionSolver expressionSolver;

    public OrderFactory(VintageTimeManager vintageTimeManager, ParcelCarrierManager parcelCarrierManager, ExpressionSolver expressionSolver) {
        this.vintageTimeManager = vintageTimeManager;
        this.parcelCarrierManager = parcelCarrierManager;
        this.expressionSolver = expressionSolver;
    }

    public Order buildOrder(String orderId, UUID userId, List<Item> itemsToBuy) {
        Map<String, List<Item>> itemsByParcelCarrier = separateItemsByParcelCarrier(itemsToBuy);

        int currentYear = vintageTimeManager.getCurrentYear();

        OrderBuilder orderBuilder = OrderBuilder.newBuilder(orderId, userId, vintageTimeManager.getCurrentDate());

        for (Map.Entry<String, List<Item>> entry : itemsByParcelCarrier.entrySet()) {
            String parcelCarrierName = entry.getKey();
            List<Item> items = entry.getValue();

            ParcelCarrier parcelCarrier = parcelCarrierManager.getCarrierByName(parcelCarrierName);

            for (Item item : items) {
                orderBuilder.addOrderedItem(item, currentYear);
                orderBuilder.addItemSatisfactionPrice(item, getSatisfactionPrice(item.getItemCondition()));
            }

            orderBuilder.addParcelCarrierShipmentCost(parcelCarrier.getName(), items.size(), getShippingCost(parcelCarrier, items.size()));
        }

        return orderBuilder.build();
    }

    private BigDecimal getShippingCost(ParcelCarrier parcelCarrier, int itemsSize) {
        BigDecimal expeditionBasePrice = parcelCarrier.getBaseValueForExpedition(itemsSize);
        BigDecimal expeditionTax = parcelCarrier.getExpeditionTax();

        Map<String, BigDecimal> expressionVariables = Map.of(
                VintageConstants.EXPEDITION_PRICE_EXPRESSION_BASE_PRICE_VARIABLE, expeditionBasePrice,
                VintageConstants.EXPEDITION_PRICE_EXPRESSION_TAX_VARIABLE, expeditionTax);

        return expressionSolver.solve(parcelCarrier.getExpeditionPriceExpression(), expressionVariables);
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
