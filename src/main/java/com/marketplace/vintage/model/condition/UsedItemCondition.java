package com.marketplace.vintage.model.condition;

import com.marketplace.vintage.Lang;

public final class UsedItemCondition implements ItemCondition {
    private final int conditionLevel;
    private final int numberOfPreviousOwners;

    public UsedItemCondition(int conditionLevel, int numberOfPreviousOwners) {
        this.conditionLevel = conditionLevel;
        this.numberOfPreviousOwners = numberOfPreviousOwners;
    }

    @Override
    public String getDisplayString() {
        return Lang.USED_ITEM_CONDITION_DISPLAY_STRING
                .apply("conditionLevel", getConditionLevel(),
                       "numberOfPreviousOwners", getNumberOfPreviousOwners());
    }

    @Override
    public ItemConditionType getType() {
        return ItemConditionType.USED;
    }

    public int getConditionLevel() {
        return conditionLevel;
    }

    public int getNumberOfPreviousOwners() {
        return numberOfPreviousOwners;
    }
}
