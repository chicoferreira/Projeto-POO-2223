package com.marketplace.vintage.model.condition;

import com.marketplace.vintage.Lang;

public class ItemConditions {

    public static final ItemCondition NEW = new ItemCondition() {
        @Override
        public String getDisplayString() {
            return Lang.NEW_ITEM_CONDITION_DISPLAY_STRING.apply();
        }
    };

    public static ItemCondition createUsed(int conditionLevel, int numberOfPreviousOwners) {
        return new UsedItemCondition(conditionLevel, numberOfPreviousOwners);
    }

}
