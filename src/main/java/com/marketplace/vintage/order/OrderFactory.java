package com.marketplace.vintage.order;

import com.marketplace.vintage.VintageConstants;
import com.marketplace.vintage.VintageTimeManager;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.expression.ExpressionSolver;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.condition.ItemCondition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

        List<OrderedItem> orderedItems = new ArrayList<>();
        Map<String, BigDecimal> parcelCarrierExpeditionPrices = new HashMap<>();

        BigDecimal totalPrice = BigDecimal.ZERO;

        // TODO: maybe refactor this to have a list of prices to apply to each item and to a group of items of the same parcel carrier

        for (Map.Entry<String, List<Item>> entry : itemsByParcelCarrier.entrySet()) {
            String parcelCarrierName = entry.getKey();
            List<Item> items = entry.getValue();

            ParcelCarrier parcelCarrier = parcelCarrierManager.getCarrierByName(parcelCarrierName);

            BigDecimal expeditionBasePrice = parcelCarrier.getBaseValueForExpedition(items.size());
            BigDecimal expeditionTax = parcelCarrier.getExpeditionTax();

            BigDecimal parcelCarrierTotalPrice = expressionSolver.solve(parcelCarrier.getExpeditionPriceExpression(), Map.of(VintageConstants.EXPEDITION_PRICE_EXPRESSION_BASE_PRICE_VARIABLE, expeditionBasePrice,
                                                                                                                             VintageConstants.EXPEDITION_PRICE_EXPRESSION_TAX_VARIABLE, expeditionTax));
            parcelCarrierExpeditionPrices.put(parcelCarrierName, parcelCarrierTotalPrice);

            totalPrice = totalPrice.add(parcelCarrierTotalPrice);

            for (Item item : items) {
                BigDecimal itemPrice = item.getFinalPrice(currentYear);
                BigDecimal satisfactionPrice = getSatisfactionPrice(item.getItemCondition());

                totalPrice = totalPrice.add(itemPrice).add(satisfactionPrice);

                OrderedItem orderedItem = new OrderedItem(item.getAlphanumericId(), 1, item.getParcelCarrierName(), itemPrice);
                orderedItems.add(orderedItem);
            }
        }

        return new Order(orderId, userId, orderedItems, parcelCarrierExpeditionPrices, totalPrice, vintageTimeManager.getCurrentDate());
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
