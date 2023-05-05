package com.marketplace.vintage;

import com.marketplace.vintage.utils.VintageDate;

import java.io.Serializable;
import java.time.LocalDateTime;

public class VintageTimeManager implements Serializable {

    public int getCurrentYear() {
        // TODO
        return 2023;
    }

    public VintageDate getCurrentDate() {
        return VintageDate.fromDate(LocalDateTime.of(2023, 1, 1, 0, 0, 0));
    }
}
