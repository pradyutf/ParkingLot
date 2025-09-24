package service.impl;

import service.interfaces.Service;

public class MaintenanceService implements Service {
    private static final String SERVICE_NAME = "MAINTENANCE";
    private static final double SERVICE_COST = 35.0;
    
    @Override
    public String getName() {
        return SERVICE_NAME;
    }
    
    @Override
    public double getCost() {
        return SERVICE_COST;
    }
}