package com.marketplace.vintage.carrier;

import java.util.UUID;

public class ParcelCarrier {

    private final UUID uuid;
    private final String name;

    private final double smallTax;
    private final double mediumTax;
    private final double largeTax;

    public ParcelCarrier(String name) {
        this(name, 0D, 0D, 0D);
    }

    public ParcelCarrier(UUID uuid, String name) {
        this(uuid, name, 0D, 0D, 0D);
    }

    public ParcelCarrier(String name, double smallTax, double mediumTax, double largeTax) {
        this(UUID.randomUUID(), name, smallTax, mediumTax, largeTax);
    }

    public ParcelCarrier(UUID uuid, String name, double smallTax, double mediumTax, double largeTax) {
        this.uuid = uuid;
        this.name = name;
        this.smallTax = smallTax;
        this.mediumTax = mediumTax;
        this.largeTax = largeTax;
    }

    public UUID getId() {
        return uuid;
    }

    public String getName() {
        return name;
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
