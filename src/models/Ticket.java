package models;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot slot;
    private Date entryTime;
    private Gate entryGate;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot slot, Date entryTime, Gate entryGate) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
        this.entryGate = entryGate;
    }

    // Getters
    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public Gate getEntryGate() {
        return entryGate;
    }

    // Setters
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setSlot(ParkingSlot slot) {
        this.slot = slot;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public void setEntryGate(Gate entryGate) {
        this.entryGate = entryGate;
    }
}