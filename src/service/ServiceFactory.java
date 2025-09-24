package service;

import service.interfaces.Service;
import service.impl.*;

/**
 * Factory class for creating standard parking lot services
 */
public class ServiceFactory {
    
    // Singleton instances for common services
    private static final Service EV_CHARGING = new EVChargingService();
    private static final Service CLEANING = new CleaningService();
    private static final Service MAINTENANCE = new MaintenanceService();
    private static final Service TIRE_SERVICE = new TireService();
    
    /**
     * Get EV Charging service
     */
    public static Service getEVChargingService() {
        return EV_CHARGING;
    }
    
    /**
     * Get Cleaning service
     */
    public static Service getCleaningService() {
        return CLEANING;
    }
    
    /**
     * Get Maintenance service
     */
    public static Service getMaintenanceService() {
        return MAINTENANCE;
    }
    
    /**
     * Get Tire service
     */
    public static Service getTireService() {
        return TIRE_SERVICE;
    }
    
    /**
     * Create a custom service with specified name and cost
     */
    public static Service createCustomService(String name, double cost) {
        return new Service() {
            @Override
            public String getName() {
                return name;
            }
            
            @Override
            public double getCost() {
                return cost;
            }
        };
    }
}