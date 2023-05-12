package com.marketplace.vintage.item.condition;

import com.marketplace.vintage.VintageConstants;

public final class UsedItemCondition implements ItemCondition {
    private final int conditionLevel;
    private final int numberOfPreviousOwners;

    public UsedItemCondition(int conditionLevel, int numberOfPreviousOwners) {
        this.conditionLevel = conditionLevel;
        this.numberOfPreviousOwners = numberOfPreviousOwners;
    }

    @Override
    public String getDisplayString() {
        return VintageConstants.ITEM_CONDITION_USED_DISPLAY_STRING
                .replace("<conditionLevel>", String.valueOf(getConditionLevel()))
                .replace("<numberOfPreviousOwners>", String.valueOf(getNumberOfPreviousOwners()));
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

    @Override
    public String toString() {
        return getDisplayString();
    }
}
