package com.marketplace.vintage.model;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import org.jetbrains.annotations.NotNull;


import com.marketplace.vintage.exceptions.EntityNotFoundException;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;


public class ParcelCarrierManager {

    private final Map<UUID, ParcelCarrier> carriersById;
    private final Map<String, ParcelCarrier> carriersByName;

    public ParcelCarrierManager() {
        this.carriersById = new HashMap<>();
        this.carriersByName = new HashMap<>();
    }

    public void registerParcelCarrier(@NotNull ParcelCarrier carrier) throws Exception {
        UUID carrierId = carrier.getId();
        String carrierName = carrier.getName();

        if(this.carriersById.get(carrierId) != null && this.carriersByName.get(carrierName) != null )
            throw new EntityAlreadyExistsException("ID/Name is already associated to a Carrier");

        this.carriersById.put(carrierId, carrier);
        this.carriersByName.put(carrierName, carrier);
    }

    public ParcelCarrier getCarrierById(UUID id) throws Exception {
        ParcelCarrier carrier = this.carriersById.get(id);

        if(carrier == null) {
            throw new EntityNotFoundException("Could not find carrier");
        }
        return carrier;
    }

    public ParcelCarrier getCarrierByName(String name) throws Exception {
        ParcelCarrier carrier = this.carriersByName.get(name);

        if(carrier == null) {
            throw new EntityNotFoundException("Could not find carrier");
        }
        return carrier;
    }

}
