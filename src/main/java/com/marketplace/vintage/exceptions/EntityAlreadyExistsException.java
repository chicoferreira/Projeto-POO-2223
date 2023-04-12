package com.marketplace.vintage.exceptions;

import java.lang.RuntimeException;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String exception){
        super("Entity already exists:" + exception);
    }
}
