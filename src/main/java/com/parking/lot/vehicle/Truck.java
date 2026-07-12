package com.parking.lot.vehicle;

import com.parking.lot.enums.SpotType;
import com.parking.lot.enums.VehicleType;

public class Truck extends Vehicle{
    public Truck(String licensePlate){
        super(licensePlate, VehicleType.TRUCK);
    }

    @Override
    public SpotType getRequiredSpotType() {
        return SpotType.LARGE;
    }
}
