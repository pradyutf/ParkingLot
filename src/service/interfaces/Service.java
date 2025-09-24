package service.interfaces;

/**
 * Service interface for parking lot services
 * Each service has a name and associated cost
 */
public interface Service {
    /**
     * Get the name of the service
     * @return service name
     */
    String getName();
    
    /**
     * Get the cost of the service
     * @return service cost in currency units
     */
    double getCost();
    
    /**
     * Check if this service matches another service by name
     * @param other the other service to compare
     * @return true if services match, false otherwise
     */
    default boolean matches(Service other) {
        return this.getName().equals(other.getName());
    }
}