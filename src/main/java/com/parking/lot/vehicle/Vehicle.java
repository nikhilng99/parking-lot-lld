package com.parking.lot.vehicle;

import com.parking.lot.enums.SpotType;
import com.parking.lot.enums.VehicleType;

public abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType vehicleType;

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public abstract SpotType getRequiredSpotType();

    public VehicleType getType() {
        return vehicleType;
    }
}
