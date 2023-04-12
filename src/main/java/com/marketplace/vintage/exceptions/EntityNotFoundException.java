package com.marketplace.vintage.exceptions;

import java.lang.RuntimeException;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String exception) {
        super("Entity not found:" + exception);
    }
}
