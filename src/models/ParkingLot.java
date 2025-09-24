package models;

import enums.SlotAvailability;
import strategy.PricingStrategy;
import strategy.SlotAllotmentStrategy;
import java.util.List;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe ParkingLot implementation
 * 
 * Thread-safety approach:
 * - Uses ReentrantLock for slot allocation/deallocation operations
 * - Prevents race conditions where multiple threads try to allocate the same slot
 * - Ensures slot state changes are visible to all threads
 * - Uses synchronized methods for gate operations (less frequent)
 * - Provides thread-safe utility methods for slot counting
 */
public class ParkingLot {
    private List<Gate> gates;
    private List<ParkingSlot> slots;
    private PricingStrategy pricingStrategy;
    private SlotAllotmentStrategy slotAllotmentStrategy;
    
    // Lock for thread-safe slot allocation and deallocation
    private final ReentrantLock slotLock = new ReentrantLock();

    public ParkingLot(List<Gate> gates, List<ParkingSlot> slots, 
                     PricingStrategy pricingStrategy, SlotAllotmentStrategy slotAllotmentStrategy) {
        this.gates = gates;
        this.slots = slots;
        this.pricingStrategy = pricingStrategy;
        this.slotAllotmentStrategy = slotAllotmentStrategy;
    }

    /**
     * Parks a vehicle by finding an appropriate slot and creating a ticket
     * Thread-safe implementation using ReentrantLock to prevent concurrent slot allocation
     */
    public Ticket parkVehicle(Vehicle vehicle, Gate entryGate) {
        slotLock.lock();
        try {
            // Find an appropriate slot using the slot allotment strategy
            ParkingSlot assignedSlot = slotAllotmentStrategy.findSlot(vehicle, entryGate, slots);
            
            if (assignedSlot == null) {
                throw new RuntimeException("No suitable parking slot available for vehicle: " + vehicle.getVehicleId());
            }
            
            // Double-check slot availability before allocation (race condition protection)
            if (assignedSlot.getAvailability() != SlotAvailability.AVAILABLE) {
                // Slot was taken by another thread, try again
                assignedSlot = slotAllotmentStrategy.findSlot(vehicle, entryGate, slots);
                if (assignedSlot == null) {
                    throw new RuntimeException("No suitable parking slot available for vehicle: " + vehicle.getVehicleId());
                }
            }
            
            // Mark the slot as occupied atomically
            assignedSlot.setAvailability(SlotAvailability.OCCUPIED);
            
            // Create and return ticket
            String ticketId = generateTicketId();
            Date entryTime = new Date();
            
            return new Ticket(ticketId, vehicle, assignedSlot, entryTime, entryGate);
        } finally {
            slotLock.unlock();
        }
    }

    /**
     * Unparks a vehicle by freeing the slot and calculating the parking fee
     * Thread-safe implementation using ReentrantLock to ensure slot state changes are visible
     */
    public double unparkVehicle(Ticket ticket, Gate exitGate) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }
        
        slotLock.lock();
        try {
            // Get the parking slot from ticket
            ParkingSlot slot = ticket.getSlot();
            
            // Verify the slot is currently occupied (defensive programming)
            if (slot.getAvailability() != SlotAvailability.OCCUPIED) {
                throw new IllegalStateException("Attempting to unpark from a slot that is not occupied: " + slot.getSlotId());
            }
            
            // Free the parking slot atomically
            slot.setAvailability(SlotAvailability.AVAILABLE);
            
            // Calculate parking fee (can be done outside lock, but keeping it inside for consistency)
            Date exitTime = new Date();
            double fee = pricingStrategy.calculateFee(ticket, exitTime);
            
            return fee;
        } finally {
            slotLock.unlock();
        }
    }

    /**
     * Generate a unique ticket ID
     */
    private String generateTicketId() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters
    public List<Gate> getGates() {
        return gates;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public SlotAllotmentStrategy getSlotAllotmentStrategy() {
        return slotAllotmentStrategy;
    }

    // Setters
    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    public void setSlots(List<ParkingSlot> slots) {
        this.slots = slots;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public void setSlotAllotmentStrategy(SlotAllotmentStrategy slotAllotmentStrategy) {
        this.slotAllotmentStrategy = slotAllotmentStrategy;
    }
    
    /**
     * Add a parking slot to the parking lot (thread-safe)
     */
    public void addParkingSlot(ParkingSlot slot) {
        slotLock.lock();
        try {
            this.slots.add(slot);
        } finally {
            slotLock.unlock();
        }
    }
    
    /**
     * Add a gate to the parking lot (thread-safe)
     */
    public synchronized void addGate(Gate gate) {
        this.gates.add(gate);
    }
    
    /**
     * Get available slot count (thread-safe)
     */
    public int getAvailableSlotCount() {
        slotLock.lock();
        try {
            return (int) slots.stream()
                    .filter(slot -> slot.getAvailability() == SlotAvailability.AVAILABLE)
                    .count();
        } finally {
            slotLock.unlock();
        }
    }
    
    /**
     * Get occupied slot count (thread-safe)
     */
    public int getOccupiedSlotCount() {
        slotLock.lock();
        try {
            return (int) slots.stream()
                    .filter(slot -> slot.getAvailability() == SlotAvailability.OCCUPIED)
                    .count();
        } finally {
            slotLock.unlock();
        }
    }
}