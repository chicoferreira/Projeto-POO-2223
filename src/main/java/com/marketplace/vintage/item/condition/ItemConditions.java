package com.marketplace.vintage.item.condition;

import com.marketplace.vintage.VintageConstants;

public class ItemConditions {

    public static final ItemCondition NEW = new ItemCondition() {
        @Override
        public String getDisplayString() {
            return VintageConstants.ITEM_CONDITION_NEW_DISPLAY_STRING;
        }

        @Override
        public ItemConditionType getType() {
            return ItemConditionType.NEW;
        }

        @Override
        public String toString() {
            return getDisplayString();
        }
    };

    public static ItemCondition createUsed(int conditionLevel, int numberOfPreviousOwners) {
        if (conditionLevel < 1 || conditionLevel > 10)
            throw new IllegalArgumentException("conditionLevel must be between 1 and 10");
        if (numberOfPreviousOwners < 0)
            throw new IllegalArgumentException("numberOfPreviousOwners must be >= 0");

        return new UsedItemCondition(conditionLevel, numberOfPreviousOwners);
    }

}
