package com.marketplace.vintage;

import com.marketplace.vintage.utils.VintageDate;

import java.io.Serializable;

public class VintageTimeManager implements Serializable {

    private VintageDate currentDate;

    public VintageTimeManager(VintageDate currentDate) {
        this.currentDate = currentDate;
    }

    public int getCurrentYear() {
        return this.currentDate.getYear();
    }

    public VintageDate getCurrentDate() {
        return this.currentDate; // VintageDate is immutable
    }

    public VintageDate advanceTime(int days) {
        return this.currentDate = this.currentDate.plusDays(days);
    }
}
