package com.marketplace.vintage.item.condition;

import java.io.Serializable;

public interface ItemCondition extends Serializable {

    String getDisplayString();

    ItemConditionType getType();

}
