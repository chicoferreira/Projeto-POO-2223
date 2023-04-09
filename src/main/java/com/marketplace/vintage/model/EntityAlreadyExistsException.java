package com.marketplace.vintage.model;

public class EntityAlreadyExistsException extends Exception {
    
    public EntityAlreadyExistsException(String exception) throws Exception {
        throw new Exception("Entity already exists:" + exception);
    }
}
