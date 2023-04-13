package com.marketplace.vintage;

import com.marketplace.vintage.item.condition.UsedItemCondition;

public class Main {
    public static void main(String[] args) {
        System.out.println(new UsedItemCondition(10, 100).getDisplayString());
    }
}