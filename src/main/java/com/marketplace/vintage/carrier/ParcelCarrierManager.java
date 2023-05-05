package com.marketplace.vintage.carrier;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.item.ItemType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParcelCarrierManager {

    private final Map<String, ParcelCarrier> carriersByName;

    public ParcelCarrierManager() {
        this.carriersByName = new HashMap<>();
    }

    public void registerParcelCarrier(@NotNull ParcelCarrier carrier) {
        String carrierName = carrier.getName();
        if (this.carriersByName.containsKey(carrierName)) {
            throw new EntityAlreadyExistsException("A carrier with that name already exists");
        }

        this.carriersByName.put(carrierName, carrier);
    }

    public ParcelCarrier getCarrierByName(String name) {
        ParcelCarrier carrier = this.carriersByName.get(name);

        if (carrier == null) {
            throw new EntityNotFoundException("A carrier with the name " + name + " was not found");
        }

        return carrier;
    }

    public boolean containsCarrierByName(String name) {
        return this.carriersByName.containsKey(name);
    }

    public List<ParcelCarrier> getAll() {
        return new ArrayList<>(carriersByName.values());
    }

    public List<ParcelCarrier> getAllCompatibleWith(ItemType itemType) {
        return carriersByName.values().stream().filter(carrier -> carrier.canDeliverItemType(itemType)).collect(Collectors.toList());
    }
}
