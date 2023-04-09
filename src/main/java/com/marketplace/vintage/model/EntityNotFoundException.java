package com.marketplace.vintage.model;

import javax.swing.text.html.parser.Entity;

public class EntityNotFoundException extends Exception {
    private Class<?> entity;

    public EntityNotFoundException(ParcelCarrier carrierTaken, String exception) throws Exception {

        throw new Exception(exception);

    }
}
