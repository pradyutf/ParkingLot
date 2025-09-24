package models;

import enums.GateType;

public class Gate {
    private String gateId;
    private GateType gateType;

    public Gate(String gateId, GateType gateType) {
        this.gateId = gateId;
        this.gateType = gateType;
    }

    // Getters
    public String getGateId() {
        return gateId;
    }

    public GateType getGateType() {
        return gateType;
    }

    // Setters
    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public void setGateType(GateType gateType) {
        this.gateType = gateType;
    }
}