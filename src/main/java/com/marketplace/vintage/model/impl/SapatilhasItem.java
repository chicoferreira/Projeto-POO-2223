package com.marketplace.vintage.model.impl;

import com.marketplace.vintage.model.Item;
import com.marketplace.vintage.model.condition.ItemCondition;
import com.marketplace.vintage.model.condition.UsedItemCondition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class SapatilhasItem extends Item {

    private final int size;
    private final boolean hasLaces;
    private final String color;
    private final int collectionYear;

    public SapatilhasItem(ItemCondition itemCondition,
                          String description,
                          String brand,
                          String alphanumericCode,
                          BigDecimal basePrice,
                          UUID parcelCarrierUuid,
                          int size,
                          boolean hasLaces,
                          String color,
                          int collectionYear) {
        super(itemCondition, description, brand, alphanumericCode, basePrice, parcelCarrierUuid);
        this.size = size;
        this.hasLaces = hasLaces;
        this.color = color;
        this.collectionYear = collectionYear;
    }

    public int getSize() {
        return size;
    }

    public boolean hasLaces() {
        return hasLaces;
    }

    public String getColor() {
        return color;
    }

    public int getCollectionYear() {
        return collectionYear;
    }

    @Override
    public BigDecimal getPriceCorrection(int currentYear) {
        // TODO: clarificar " Apenas há lugar aplicação de desconto ... ou em sapatilhas novas acima do tamanho 45." no enunciado
        // Qual a formula?
        ItemCondition itemCondition = getItemCondition();
        if (itemCondition instanceof UsedItemCondition usedItemCondition) {
            BigDecimal previousOwnersMultiplier = BigDecimal.valueOf(usedItemCondition.getNumberOfPreviousOwners() * 0.5);
            BigDecimal conditionMultiplier = BigDecimal.valueOf(usedItemCondition.getConditionLevel() * 0.1);
            return getBasePrice().negate()
                                 .divide(previousOwnersMultiplier, 2, RoundingMode.UP)
                                 .multiply(conditionMultiplier);
        }
        return BigDecimal.ZERO;
    }
}
