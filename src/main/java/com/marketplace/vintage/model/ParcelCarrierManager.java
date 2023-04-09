package com.marketplace.vintage.model;

import org.jetbrains.annotations.NotNull;


import com.marketplace.vintage.model.EntityNotFoundException;

import java.rmi.AlreadyBoundException;
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

    public void registerParcelCarrier(@NotNull ParcelCarrier carrier) throws AlreadyBoundException {
        UUID carrierId = carrier.getId();
        String carrierName = carrier.getName();

        ParcelCarrier hashValueId = this.carriersById.putIfAbsent( carrierId, carrier);
        ParcelCarrier hashValueName = this.carriersByName.putIfAbsent( carrierName, carrier);

        if(hashValueName != null && hashValueId != null )
            throw new AlreadyBoundException("ID/Name is already associated to a Carrier");

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
