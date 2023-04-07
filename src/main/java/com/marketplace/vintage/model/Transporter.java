package com.marketplace.vintage.model;

public class Transporter {

    private final String name;
    private final double taxSmall; //Taxa 1 item
    private final double taxMedium; //Taxa 2 a 5 items
    private final double taxLarge; //Taxa mais de 5 items


    public Transporter(String name, Double taxSmall, Double taxMedium, Double taxLarge) {
        this.name = name;
        this.taxSmall = taxSmall;
        this.taxMedium = taxMedium;
        this.taxLarge = taxLarge;
    }

    public String getName() {
        return name;
    }

    public Double getTaxSmall() {
        return taxSmall;
    }

    public Double getTaxMedium() {
        return taxMedium;
    }

    public Double getTaxLarge() {
        return taxLarge;
    }

}
