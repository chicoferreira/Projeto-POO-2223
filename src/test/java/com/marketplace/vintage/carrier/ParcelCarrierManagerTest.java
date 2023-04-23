package com.marketplace.vintage.carrier;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class ParcelCarrierManagerTest {

    @Test
    void testParcelCarrierManager() {
        ParcelCarrierManager parcelCarrierManager = new ParcelCarrierManager();

        String testName = "DHL";

        parcelCarrierManager.registerParcelCarrier(ParcelCarrierFactory.createNormalParcelCarrier(testName));

        ParcelCarrier parcelCarrier = parcelCarrierManager.getCarrierByName(testName);
        assertEquals("DHL", parcelCarrier.getName());

        assertThrowsExactly(EntityNotFoundException.class, () -> parcelCarrierManager.getCarrierByName("UPS"));
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> parcelCarrierManager.registerParcelCarrier(ParcelCarrierFactory.createNormalParcelCarrier(testName)));
    }
}