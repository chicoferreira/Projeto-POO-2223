package com.marketplace.vintage.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

/**
 * Class to wrap the date of the program
 */
public class VintageDate {

    public static final Comparator<VintageDate> COMPARATOR = Comparator.comparingLong(VintageDate::getTickValue);

    private static final LocalDateTime START_DATE = LocalDateTime.of(2020, 1, 1, 0, 0);

    private final long tick;

    public VintageDate(long tick) {
        this.tick = tick;
    }

    public long getTickValue() {
        return tick;
    }

    public LocalDateTime toDate() {
        return START_DATE.plusMinutes(tick);
    }

    public static VintageDate fromDate(LocalDateTime date) {
        long until = START_DATE.until(date, ChronoUnit.MINUTES);
        if (until < 0) throw new IllegalArgumentException("Date is before the start of the program");

        return new VintageDate(until);
    }
}
