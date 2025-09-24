package service;

import models.Gate;
import models.ParkingSlot;

public class DistanceCalculatorService {
    
    /**
     * Calculate distance between a gate and a parking slot
     * This implementation reads from the slot's distance map
     */
    public static int calculateDistance(Gate gate, ParkingSlot slot) {
        return slot.getDistanceFromGate(gate);
    }
    
    /**
     * Alternative method for calculating distance based on coordinates
     * This is a placeholder implementation that could use actual coordinate-based calculation
     */
    public static int calculateDistance(int gateX, int gateY, int slotX, int slotY) {
        // Simple Manhattan distance calculation
        return Math.abs(gateX - slotX) + Math.abs(gateY - slotY);
    }
}