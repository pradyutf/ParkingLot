package strategy;

import models.Vehicle;
import models.Gate;
import models.ParkingSlot;
import enums.SlotAvailability;
import enums.SlotType;
import enums.VehicleType;
import service.interfaces.Service;
import java.util.List;

public class NearestMatchingSlotStrategy implements SlotAllotmentStrategy {
    
    @Override
    public ParkingSlot findSlot(Vehicle vehicle, Gate entryGate, List<ParkingSlot> slots) {
        ParkingSlot bestSlot = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (ParkingSlot slot : slots) {
            // Check if slot is available
            if (slot.getAvailability() != SlotAvailability.AVAILABLE) {
                continue;
            }
            
            // Check if slot type matches vehicle type
            if (!isSlotCompatible(vehicle.getVehicleType(), slot.getSlotType())) {
                continue;
            }
            
            // Check if slot supports all required services
            if (!supportsAllRequiredServices(slot.getSupportedServices(), vehicle.getRequiredServices())) {
                continue;
            }
            
            // Find slot with minimum distance
            int distance = slot.getDistanceFromGate(entryGate);
            if (distance < minDistance) {
                minDistance = distance;
                bestSlot = slot;
            }
        }
        
        return bestSlot;
    }
    
    private boolean isSlotCompatible(VehicleType vehicleType, SlotType slotType) {
        switch (vehicleType) {
            case BIKE:
                return slotType == SlotType.SMALL || slotType == SlotType.MEDIUM || slotType == SlotType.LARGE;
            case CAR:
                return slotType == SlotType.MEDIUM || slotType == SlotType.LARGE;
            case TRUCK:
                return slotType == SlotType.LARGE;
            default:
                return false;
        }
    }
    
    /**
     * Check if the slot's supported services include all the required services
     * Services are matched by name
     */
    private boolean supportsAllRequiredServices(List<Service> supportedServices, List<Service> requiredServices) {
        for (Service requiredService : requiredServices) {
            boolean serviceFound = false;
            for (Service supportedService : supportedServices) {
                if (supportedService.matches(requiredService)) {
                    serviceFound = true;
                    break;
                }
            }
            if (!serviceFound) {
                return false;
            }
        }
        return true;
    }
}