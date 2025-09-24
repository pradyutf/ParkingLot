import models.*;
import enums.*;
import strategy.*;
import service.ServiceFactory;
import service.interfaces.Service;
import java.util.*;

public class ParkingLotDemo {
    public static void main(String[] args) {
        System.out.println("=== Parking Lot System Demo ===\n");
        
        // Create gates
        Gate entryGate1 = new Gate("ENTRY-1", GateType.ENTRY);
        Gate entryGate2 = new Gate("ENTRY-2", GateType.ENTRY);
        Gate exitGate1 = new Gate("EXIT-1", GateType.EXIT);
        Gate exitGate2 = new Gate("EXIT-2", GateType.EXIT);
        
        List<Gate> gates = Arrays.asList(entryGate1, entryGate2, exitGate1, exitGate2);
        
        // Create parking slots using builder pattern
        ParkingSlot slot1 = new ParkingSlotBuilder()
            .withSlotId("SLOT-001")
            .withSlotType(SlotType.SMALL)
            .withService(ServiceFactory.getCleaningService())
            .withDistance(entryGate1, 10)
            .withDistance(entryGate2, 25)
            .build();
            
        ParkingSlot slot2 = new ParkingSlotBuilder()
            .withSlotId("SLOT-002")
            .withSlotType(SlotType.MEDIUM)
            .withService(ServiceFactory.getEVChargingService())
            .withService(ServiceFactory.getCleaningService())
            .withDistance(entryGate1, 15)
            .withDistance(entryGate2, 20)
            .build();
            
        ParkingSlot slot3 = new ParkingSlotBuilder()
            .withSlotId("SLOT-003")
            .withSlotType(SlotType.LARGE)
            .withService(ServiceFactory.getMaintenanceService())
            .withDistance(entryGate1, 30)
            .withDistance(entryGate2, 5)
            .build();
            
        ParkingSlot slot4 = new ParkingSlotBuilder()
            .withSlotId("SLOT-004")
            .withSlotType(SlotType.MEDIUM)
            .withService(ServiceFactory.getEVChargingService())
            .withDistance(entryGate1, 20)
            .withDistance(entryGate2, 15)
            .build();
        
        List<ParkingSlot> slots = Arrays.asList(slot1, slot2, slot3, slot4);
        
        // Create strategies
        PricingStrategy pricingStrategy = new FixedPricingStrategy();
        SlotAllotmentStrategy slotAllotmentStrategy = new NearestMatchingSlotStrategy();
        
        // Create parking lot
        ParkingLot parkingLot = new ParkingLot(gates, slots, pricingStrategy, slotAllotmentStrategy);
        
        // Create vehicles
        Vehicle bike = new Vehicle("BIKE-001", VehicleType.BIKE, FuelType.PETROL, 
                                  Arrays.asList(ServiceFactory.getCleaningService()));
        
        Vehicle electricCar = new Vehicle("CAR-001", VehicleType.CAR, FuelType.ELECTRIC, 
                                         Arrays.asList(ServiceFactory.getEVChargingService()));
        
        Vehicle truck = new Vehicle("TRUCK-001", VehicleType.TRUCK, FuelType.DIESEL, 
                                   Arrays.asList(ServiceFactory.getMaintenanceService()));
        
        // Simulate parking operations
        try {
            System.out.println("1. Parking vehicles...\n");
            
            // Park bike
            Ticket bikeTicket = parkingLot.parkVehicle(bike, entryGate1);
            System.out.println("✓ Bike parked successfully!");
            System.out.println("  Ticket ID: " + bikeTicket.getTicketId());
            System.out.println("  Slot: " + bikeTicket.getSlot().getSlotId());
            System.out.println("  Entry Time: " + bikeTicket.getEntryTime());
            System.out.println();
            
            // Simulate some time passing
            Thread.sleep(1000);
            
            // Park electric car
            Ticket carTicket = parkingLot.parkVehicle(electricCar, entryGate1);
            System.out.println("✓ Electric car parked successfully!");
            System.out.println("  Ticket ID: " + carTicket.getTicketId());
            System.out.println("  Slot: " + carTicket.getSlot().getSlotId());
            System.out.println("  Entry Time: " + carTicket.getEntryTime());
            System.out.println();
            
            // Simulate some time passing
            Thread.sleep(1000);
            
            // Park truck
            Ticket truckTicket = parkingLot.parkVehicle(truck, entryGate2);
            System.out.println("✓ Truck parked successfully!");
            System.out.println("  Ticket ID: " + truckTicket.getTicketId());
            System.out.println("  Slot: " + truckTicket.getSlot().getSlotId());
            System.out.println("  Entry Time: " + truckTicket.getEntryTime());
            System.out.println();
            
            // Simulate parking duration
            System.out.println("2. Simulating parking duration (2 seconds)...\n");
            Thread.sleep(2000);
            
            System.out.println("3. Unparking vehicles...\n");
            
            // Unpark bike
            double bikeFee = parkingLot.unparkVehicle(bikeTicket, exitGate1);
            System.out.println("✓ Bike unparked successfully!");
            System.out.println("  Parking Fee: $" + String.format("%.2f", bikeFee));
            System.out.println();
            
            // Unpark electric car
            double carFee = parkingLot.unparkVehicle(carTicket, exitGate2);
            System.out.println("✓ Electric car unparked successfully!");
            System.out.println("  Parking Fee: $" + String.format("%.2f", carFee));
            System.out.println();
            
            // Unpark truck
            double truckFee = parkingLot.unparkVehicle(truckTicket, exitGate1);
            System.out.println("✓ Truck unparked successfully!");
            System.out.println("  Parking Fee: $" + String.format("%.2f", truckFee));
            System.out.println();
            
            // Display summary
            System.out.println("=== Summary ===");
            System.out.println("Total vehicles processed: 3");
            System.out.println("Total revenue: $" + String.format("%.2f", bikeFee + carFee + truckFee));
            System.out.println("All parking slots are now available again.");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Demo completed successfully! ===");
    }
}