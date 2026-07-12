package com.parking.lot.vehicle;

import com.parking.lot.enums.SpotType;
import com.parking.lot.enums.VehicleType;

public class Suv extends Vehicle{
    public Suv(String licensePlate) {
        super(licensePlate, VehicleType.SUV);
    }

    @Override
    public SpotType getRequiredSpotType() {
        return SpotType.LARGE;
    }
}
