package models;

import enums.SlotType;
import enums.SlotAvailability;
import service.interfaces.Service;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class ParkingSlotBuilder {
    private String slotId;
    private SlotType slotType;
    private SlotAvailability availability = SlotAvailability.AVAILABLE;
    private List<Service> supportedServices = new ArrayList<>();
    private Map<Gate, Integer> distanceFromGate = new HashMap<>();

    public ParkingSlotBuilder withSlotId(String slotId) {
        this.slotId = slotId;
        return this;
    }

    public ParkingSlotBuilder withSlotType(SlotType slotType) {
        this.slotType = slotType;
        return this;
    }

    public ParkingSlotBuilder withAvailability(SlotAvailability availability) {
        this.availability = availability;
        return this;
    }

    public ParkingSlotBuilder withService(Service service) {
        this.supportedServices.add(service);
        return this;
    }

    public ParkingSlotBuilder withDistance(Gate gate, int distance) {
        this.distanceFromGate.put(gate, distance);
        return this;
    }

    public ParkingSlot build() {
        if (slotId == null || slotType == null) {
            throw new IllegalArgumentException("SlotId and SlotType are required");
        }
        return new ParkingSlot(slotId, slotType, availability, 
                              new ArrayList<>(supportedServices), 
                              new HashMap<>(distanceFromGate));
    }
}