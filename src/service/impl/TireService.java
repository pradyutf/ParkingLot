package service.impl;

import service.interfaces.Service;

public class TireService implements Service {
    private static final String SERVICE_NAME = "TIRE_SERVICE";
    private static final double SERVICE_COST = 20.0;
    
    @Override
    public String getName() {
        return SERVICE_NAME;
    }
    
    @Override
    public double getCost() {
        return SERVICE_COST;
    }
}