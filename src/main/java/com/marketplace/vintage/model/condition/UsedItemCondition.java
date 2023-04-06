package com.marketplace.vintage.model.condition;

import com.marketplace.vintage.Lang;

public class UsedItemCondition implements ItemCondition {

    private final int conditionLevel;
    private final int numberOfPreviousOwners;

    public UsedItemCondition(int conditionLevel, int numberOfPreviousOwners) {
        this.conditionLevel = conditionLevel;
        this.numberOfPreviousOwners = numberOfPreviousOwners;
    }

    public int getConditionLevel() {
        return conditionLevel;
    }

    public int getNumberOfPreviousOwners() {
        return numberOfPreviousOwners;
    }

    @Override
    public String getDisplayString() {
        return Lang.USED_ITEM_CONDITION_DISPLAY_STRING
                .apply("conditionLevel", getConditionLevel(),
                       "numberOfPreviousOwners", getNumberOfPreviousOwners());
    }
}
