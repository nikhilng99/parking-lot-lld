package com.parking.lot.vehicle;

import com.parking.lot.enums.SpotType;
import com.parking.lot.enums.VehicleType;

public class Car extends Vehicle{
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }

    @Override
    public SpotType getRequiredSpotType() {
        return SpotType.COMPACT;
    }
}
