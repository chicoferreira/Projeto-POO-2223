package com.marketplace.vintage.model.condition;

import com.marketplace.vintage.Lang;

public class ItemConditions {

    public static final ItemCondition NEW = new ItemCondition() {
        @Override
        public String getDisplayString() {
            return Lang.NEW_ITEM_CONDITION_DISPLAY_STRING.apply();
        }

        @Override
        public ItemConditionType getType() {
            return ItemConditionType.NEW;
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
