package models;

import enums.VehicleType;
import enums.FuelType;
import service.interfaces.Service;
import java.util.List;

public class Vehicle {
    private String vehicleId;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private List<Service> requiredServices;

    public Vehicle(String vehicleId, VehicleType vehicleType, FuelType fuelType, List<Service> requiredServices) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.requiredServices = requiredServices;
    }

    // Getters
    public String getVehicleId() {
        return vehicleId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public List<Service> getRequiredServices() {
        return requiredServices;
    }

    // Setters
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setRequiredServices(List<Service> requiredServices) {
        this.requiredServices = requiredServices;
    }
}