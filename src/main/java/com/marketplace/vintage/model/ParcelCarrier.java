package com.marketplace.vintage.model;

import java.util.UUID;

public class ParcelCarrier {

    private final String name;
    private final UUID id;

    private final double smallTax;
    private final double mediumTax;
    private final double largeTax;

    public ParcelCarrier(String name, Double smallTax, Double mediumTax, Double largeTax) {
        this(UUID.randomUUID(), name, smallTax, mediumTax, largeTax);
    }

    public ParcelCarrier(UUID uuid, String name, Double smallTax, Double mediumTax, Double largeTax) {
        this.id = uuid;
        this.name = name;
        this.smallTax = smallTax;
        this.mediumTax = mediumTax;
        this.largeTax = largeTax;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public double getTax(int amountOfParcels) {
        if (amountOfParcels <= 0)
            throw new IllegalArgumentException("amountOfParcels must be a positive non-zero");
        if (amountOfParcels == 1)
            return smallTax;
        if (amountOfParcels <= 5)
            return mediumTax;

        return largeTax;
    }
}
