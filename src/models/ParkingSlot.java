package models;

import enums.SlotType;
import enums.SlotAvailability;
import service.interfaces.Service;
import java.util.List;
import java.util.Map;

public class ParkingSlot {
    private String slotId;
    private SlotType slotType;
    private SlotAvailability availability;
    private List<Service> supportedServices;
    private Map<Gate, Integer> distanceFromGate;

    // Package-private constructor - only accessible via ParkingSlotBuilder
    ParkingSlot(String slotId, SlotType slotType, SlotAvailability availability, 
               List<Service> supportedServices, Map<Gate, Integer> distanceFromGate) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.availability = availability;
        this.supportedServices = supportedServices;
        this.distanceFromGate = distanceFromGate;
    }

    public int getDistanceFromGate(Gate gate) {
        return distanceFromGate.getOrDefault(gate, Integer.MAX_VALUE);
    }

    // Getters
    public String getSlotId() {
        return slotId;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public SlotAvailability getAvailability() {
        return availability;
    }

    public List<Service> getSupportedServices() {
        return supportedServices;
    }

    public Map<Gate, Integer> getDistanceFromGate() {
        return distanceFromGate;
    }

    // Setters
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }

    public void setAvailability(SlotAvailability availability) {
        this.availability = availability;
    }

    public void setSupportedServices(List<Service> supportedServices) {
        this.supportedServices = supportedServices;
    }

    public void setDistanceFromGate(Map<Gate, Integer> distanceFromGate) {
        this.distanceFromGate = distanceFromGate;
    }
}