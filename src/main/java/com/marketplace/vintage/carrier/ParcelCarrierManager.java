package com.marketplace.vintage.carrier;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.item.ItemType;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParcelCarrierManager implements Serializable {

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

        return carrier.clone();
    }

    public boolean containsCarrierByName(String name) {
        return this.carriersByName.containsKey(name);
    }

    public void updateParcelCarrier(String carrierName, ParcelCarrier parcelCarrier) {
        if (!this.carriersByName.containsKey(carrierName)) {
            throw new EntityNotFoundException("A carrier with the name " + carrierName + " was not found");
        }

        this.carriersByName.put(carrierName, parcelCarrier);
    }

    public List<ParcelCarrier> getAll() {
        return carriersByName.values().stream().map(ParcelCarrier::clone).collect(Collectors.toList());
    }

    public List<ParcelCarrier> getAllCompatibleWith(ItemType itemType) {
        return getAll().stream().filter(carrier -> carrier.canDeliverItemType(itemType)).collect(Collectors.toList());
    }
}
