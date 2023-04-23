package com.marketplace.vintage.carrier;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

        if (this.carriersById.containsKey(carrierId)) {
            throw new EntityAlreadyExistsException("A carrier with that id already exists");
        }

        if (this.carriersByName.containsKey(carrierName)) {
            throw new EntityAlreadyExistsException("A carrier with that name already exists");
        }

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

    public List<ParcelCarrier> getAll() {
        return new ArrayList<>(carriersById.values());
    }
}
