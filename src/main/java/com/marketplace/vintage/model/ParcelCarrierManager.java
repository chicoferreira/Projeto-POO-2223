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

    public void registerParcelCarrier(@NotNull ParcelCarrier carrier) {
        UUID carrierId = carrier.getId();
        String carrierName = carrier.getName();

        if (this.carriersById.containsKey(carrierId) && this.carriersByName.containsKey(carrierName))
            throw new EntityAlreadyExistsException("A carrier with that name or id already exists");

        this.carriersById.put(carrierId, carrier);
        this.carriersByName.put(carrierName, carrier);
    }

    public ParcelCarrier getCarrierById(UUID id) {
        ParcelCarrier carrier = this.carriersById.get(id);

        if (carrier == null) {
            throw new EntityNotFoundException("A carrier with the id " + id + " was not found");
        }
        return carrier;
    }

    public ParcelCarrier getCarrierByName(String name) {
        ParcelCarrier carrier = this.carriersByName.get(name);

        if (carrier == null) {
            throw new EntityNotFoundException("A carrier with the name " + name + " was not found");
        }
        return carrier;
    }

}
