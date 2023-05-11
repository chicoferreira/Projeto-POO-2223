package com.marketplace.vintage.input.impl;

import java.util.Iterator;

public class BufferedInputPrompter extends BaseInputPrompter {

    private final Iterator<String> iterator;

    public BufferedInputPrompter(Iterator<String> iterator) {
        this.iterator = iterator;
    }

    @Override
    public String getInput() {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        throw new IllegalStateException("No more input available");
    }
}
