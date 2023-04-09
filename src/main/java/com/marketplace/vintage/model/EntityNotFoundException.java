package com.marketplace.vintage.model;

import javax.swing.text.html.parser.Entity;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String exception) throws Exception {
        throw new Exception("Entity not found:" + exception);
    }
}
