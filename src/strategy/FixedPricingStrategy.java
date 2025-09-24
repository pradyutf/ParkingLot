package strategy;

import models.Ticket;
import service.interfaces.Service;
import java.util.Date;

public class FixedPricingStrategy implements PricingStrategy {
    private static final double FIXED_FEE_PER_HOUR = 50.0;

    @Override
    public double calculateFee(Ticket ticket, Date exitTime) {
        long entryTimeMillis = ticket.getEntryTime().getTime();
        long exitTimeMillis = exitTime.getTime();
        long durationMillis = exitTimeMillis - entryTimeMillis;
        
        // Convert to hours (rounded up)
        double hours = Math.ceil(durationMillis / (1000.0 * 60 * 60));
        
        // Calculate base parking fee
        double baseFee = hours * FIXED_FEE_PER_HOUR;
        
        // Calculate service costs for the vehicle's required services
        double serviceCosts = calculateServiceCosts(ticket);
        
        return baseFee + serviceCosts;
    }
    
    /**
     * Calculate the total cost of services required by the vehicle
     * Only charges for services that the vehicle actually requires
     */
    private double calculateServiceCosts(Ticket ticket) {
        double totalServiceCost = 0.0;
        
        // Get the services required by the vehicle
        for (Service requiredService : ticket.getVehicle().getRequiredServices()) {
            // Check if the parking slot supports this service
            for (Service supportedService : ticket.getSlot().getSupportedServices()) {
                if (supportedService.matches(requiredService)) {
                    totalServiceCost += supportedService.getCost();
                    break; // Only charge once per required service
                }
            }
        }
        
        return totalServiceCost;
    }
}