package com.marketplace.vintage.utils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class to wrap the date of the program
 */
public class VintageDate implements Comparable<VintageDate>, Serializable {

    public static VintageDate of(int dayOfMonth, int month, int year) {
        return new VintageDate(LocalDate.of(year, month, dayOfMonth));
    }

    private final LocalDate date;

    private VintageDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate toJavaDate() {
        return date;
    }

    public int getDayOfMonth() {
        return date.getDayOfMonth();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getYear() {
        return date.getYear();
    }

    public VintageDate plusDays(int days) {
        return new VintageDate(date.plusDays(days));
    }

    public boolean isBeforeOrSame(VintageDate date) {
        return this.compareTo(date) <= 0;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", getDayOfMonth(), getMonth(), getYear());
    }

    @Override
    public int compareTo(@NotNull VintageDate o) {
        return this.toJavaDate().compareTo(o.toJavaDate());
    }

}
