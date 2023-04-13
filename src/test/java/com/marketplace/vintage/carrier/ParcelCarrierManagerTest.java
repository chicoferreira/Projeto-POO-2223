package com.marketplace.vintage.carrier;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class ParcelCarrierManagerTest {

    @Test
    void testParcelCarrierManager() {
        ParcelCarrierManager parcelCarrierManager = new ParcelCarrierManager();

        UUID testUUID = UUID.randomUUID();
        String testName = "DHL";

        parcelCarrierManager.registerParcelCarrier(new ParcelCarrier(testUUID, testName));

        ParcelCarrier parcelCarrier = parcelCarrierManager.getCarrierByName(testName);
        assertEquals("DHL", parcelCarrier.getName());
        assertEquals(testUUID, parcelCarrier.getId());

        assertThrowsExactly(EntityNotFoundException.class, () -> parcelCarrierManager.getCarrierByName("UPS"));
        assertThrowsExactly(EntityAlreadyExistsException.class, () -> parcelCarrierManager.registerParcelCarrier(new ParcelCarrier(testName)));
    }
}