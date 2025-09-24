import models.*;
import enums.*;
import strategy.*;
import service.ServiceFactory;
import service.interfaces.Service;
import java.util.*;

public class ServiceBasedPricingDemo {
    public static void main(String[] args) {
        System.out.println("=== Service-Based Pricing Parking Lot Demo ===\n");
        
        // Create gates
        Gate entryGate = new Gate("ENTRY-1", GateType.ENTRY);
        Gate exitGate = new Gate("EXIT-1", GateType.EXIT);
        
        List<Gate> gates = Arrays.asList(entryGate, exitGate);
        
        // Create parking slots with different service combinations
        ParkingSlot basicSlot = new ParkingSlotBuilder()
            .withSlotId("BASIC-001")
            .withSlotType(SlotType.MEDIUM)
            .withDistance(entryGate, 10)
            .build(); // No additional services
            
        ParkingSlot cleaningSlot = new ParkingSlotBuilder()
            .withSlotId("CLEAN-001")
            .withSlotType(SlotType.MEDIUM)
            .withService(ServiceFactory.getCleaningService())
            .withDistance(entryGate, 15)
            .build();
            
        ParkingSlot evSlot = new ParkingSlotBuilder()
            .withSlotId("EV-001")
            .withSlotType(SlotType.MEDIUM)
            .withService(ServiceFactory.getEVChargingService())
            .withDistance(entryGate, 20)
            .build();
            
        ParkingSlot premiumSlot = new ParkingSlotBuilder()
            .withSlotId("PREMIUM-001")
            .withSlotType(SlotType.LARGE)
            .withService(ServiceFactory.getEVChargingService())
            .withService(ServiceFactory.getCleaningService())
            .withService(ServiceFactory.getMaintenanceService())
            .withDistance(entryGate, 25)
            .build();
        
        List<ParkingSlot> slots = Arrays.asList(basicSlot, cleaningSlot, evSlot, premiumSlot);
        
        // Create strategies
        PricingStrategy pricingStrategy = new FixedPricingStrategy();
        SlotAllotmentStrategy slotAllotmentStrategy = new NearestMatchingSlotStrategy();
        
        // Create parking lot
        ParkingLot parkingLot = new ParkingLot(gates, slots, pricingStrategy, slotAllotmentStrategy);
        
        // Create vehicles with different service requirements
        Vehicle basicCar = new Vehicle("CAR-BASIC", VehicleType.CAR, FuelType.PETROL, 
                                      new ArrayList<>()); // No services required
        
        Vehicle cleanCar = new Vehicle("CAR-CLEAN", VehicleType.CAR, FuelType.PETROL, 
                                      Arrays.asList(ServiceFactory.getCleaningService()));
        
        Vehicle electricCar = new Vehicle("CAR-EV", VehicleType.CAR, FuelType.ELECTRIC, 
                                         Arrays.asList(ServiceFactory.getEVChargingService()));
        
        Vehicle premiumCar = new Vehicle("CAR-PREMIUM", VehicleType.CAR, FuelType.ELECTRIC, 
                                        Arrays.asList(
                                            ServiceFactory.getEVChargingService(),
                                            ServiceFactory.getCleaningService(),
                                            ServiceFactory.getMaintenanceService()
                                        ));
        
        // Demonstrate pricing differences
        List<Vehicle> testVehicles = Arrays.asList(basicCar, cleanCar, electricCar, premiumCar);
        List<Ticket> tickets = new ArrayList<>();
        
        System.out.println("=== Parking Vehicles ===");
        for (Vehicle vehicle : testVehicles) {
            try {
                Ticket ticket = parkingLot.parkVehicle(vehicle, entryGate);
                tickets.add(ticket);
                
                System.out.println("✓ " + vehicle.getVehicleId() + " parked in " + ticket.getSlot().getSlotId());
                System.out.println("  Required services: " + getServiceNames(vehicle.getRequiredServices()));
                System.out.println("  Slot supports: " + getServiceNames(ticket.getSlot().getSupportedServices()));
                System.out.println();
                
            } catch (Exception e) {
                System.out.println("✗ " + vehicle.getVehicleId() + " could not be parked: " + e.getMessage());
            }
        }
        
        // Simulate parking duration
        System.out.println("=== Simulating 2-hour parking duration ===\n");
        try {
            Thread.sleep(2000); // 2 seconds = simulated 2 hours for demo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Unpark and show pricing breakdown
        System.out.println("=== Unparking and Pricing Breakdown ===");
        double totalRevenue = 0.0;
        
        for (Ticket ticket : tickets) {
            try {
                double fee = parkingLot.unparkVehicle(ticket, exitGate);
                totalRevenue += fee;
                
                System.out.println("Vehicle: " + ticket.getVehicle().getVehicleId());
                System.out.println("  Slot: " + ticket.getSlot().getSlotId());
                System.out.println("  Base parking fee (2 hours): $100.00");
                
                // Calculate service costs separately for display
                double serviceCosts = 0.0;
                List<String> chargedServices = new ArrayList<>();
                
                for (Service requiredService : ticket.getVehicle().getRequiredServices()) {
                    for (Service supportedService : ticket.getSlot().getSupportedServices()) {
                        if (supportedService.matches(requiredService)) {
                            serviceCosts += supportedService.getCost();
                            chargedServices.add(supportedService.getName() + " ($" + 
                                              String.format("%.2f", supportedService.getCost()) + ")");
                            break;
                        }
                    }
                }
                
                System.out.println("  Service costs: " + 
                    (chargedServices.isEmpty() ? "None" : String.join(", ", chargedServices)));
                System.out.println("  Total fee: $" + String.format("%.2f", fee));
                System.out.println();
                
            } catch (Exception e) {
                System.out.println("Error unparking " + ticket.getVehicle().getVehicleId() + ": " + e.getMessage());
            }
        }
        
        // Summary
        System.out.println("=== Summary ===");
        System.out.println("Total vehicles processed: " + tickets.size());
        System.out.println("Total revenue: $" + String.format("%.2f", totalRevenue));
        System.out.println("Available slots: " + parkingLot.getAvailableSlotCount());
        System.out.println("Occupied slots: " + parkingLot.getOccupiedSlotCount());
        
        System.out.println("\n=== Service Pricing Structure ===");
        System.out.println("Base parking: $50.00/hour");
        System.out.println("EV Charging: $" + String.format("%.2f", ServiceFactory.getEVChargingService().getCost()));
        System.out.println("Cleaning: $" + String.format("%.2f", ServiceFactory.getCleaningService().getCost()));
        System.out.println("Maintenance: $" + String.format("%.2f", ServiceFactory.getMaintenanceService().getCost()));
        System.out.println("Tire Service: $" + String.format("%.2f", ServiceFactory.getTireService().getCost()));
        
        System.out.println("\n=== Demo completed successfully! ===");
    }
    
    private static String getServiceNames(List<Service> services) {
        if (services.isEmpty()) {
            return "None";
        }
        List<String> names = new ArrayList<>();
        for (Service service : services) {
            names.add(service.getName());
        }
        return String.join(", ", names);
    }
}