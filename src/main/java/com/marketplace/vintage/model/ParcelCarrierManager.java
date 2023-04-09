package com.marketplace.vintage.model;

import org.jetbrains.annotations.NotNull;


import javax.swing.text.html.parser.Entity;
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

        if(getCarrierById(carrierId) != null || getCarrierByName(carrierName) != null ){
            throw new Exception("Carrier already exists");
        }

        this.carriersById.put( carrierId, carrier);
        this.carriersByName.put( carrierName, carrier);
    }

    public ParcelCarrier getCarrierById(UUID id) throws Exception {
        if(this.carriersById.get(id) == null) throw new Exception("Carrier doesnt exist");
        return this.carriersById.get(id);
    }

    public ParcelCarrier getCarrierByName(String name) throws Exception {
        if(this.carriersById.get(name) == null) throw new Exception("Carrier doesnt exist");
        return this.carriersByName.get(name);
    }

}
