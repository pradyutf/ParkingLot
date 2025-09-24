package strategy;

import models.Vehicle;
import models.Gate;
import models.ParkingSlot;
import java.util.List;

public interface SlotAllotmentStrategy {
    ParkingSlot findSlot(Vehicle vehicle, Gate entryGate, List<ParkingSlot> slots);
}