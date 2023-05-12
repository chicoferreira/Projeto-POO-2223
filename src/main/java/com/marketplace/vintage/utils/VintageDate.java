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

    public static VintageDate fromString(String date) {
        String[] split = date.trim().split("/");
        if (split.length != 3) {
            throw new IllegalArgumentException("Date must have the format: DD/MM/YYYY");
        }

        try {
            int dayOfMonth = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);

            return VintageDate.of(dayOfMonth, month, year);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Date must have integers in: DD/MM/YYYY");
        }
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

    public boolean isAfterOrSame(VintageDate date) {
        return this.compareTo(date) >= 0;
    }

    public boolean isAfter(VintageDate date) {
        return this.compareTo(date) > 0;
    }

    public int distance(VintageDate date) {
        return (int) Math.abs(this.toJavaDate().toEpochDay() - date.toJavaDate().toEpochDay());
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", getDayOfMonth(), getMonth(), getYear());
    }

    @Override
    public int compareTo(@NotNull VintageDate o) {
        return this.toJavaDate().compareTo(o.toJavaDate());
    }

    public boolean isBetweenInclusive(VintageDate from, VintageDate to) {
        return from.isBeforeOrSame(this) && to.isAfterOrSame(this);
    }
}
