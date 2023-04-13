package com.marketplace.vintage.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String exception) {
        super("Entity not found: " + exception);
    }
}
