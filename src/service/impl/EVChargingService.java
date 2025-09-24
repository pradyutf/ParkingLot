package service.impl;

import service.interfaces.Service;

public class EVChargingService implements Service {
    private static final String SERVICE_NAME = "EV_CHARGING";
    private static final double SERVICE_COST = 25.0;
    
    @Override
    public String getName() {
        return SERVICE_NAME;
    }
    
    @Override
    public double getCost() {
        return SERVICE_COST;
    }
}