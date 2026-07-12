package com.parking.lot.vehicle;

import com.parking.lot.enums.SpotType;
import com.parking.lot.enums.VehicleType;

public class MotorCycle extends Vehicle{
    public MotorCycle(String licensePlate){
        super(licensePlate, VehicleType.MOTORCYCLE);
    }

    @Override
    public SpotType getRequiredSpotType() {
        return SpotType.MOTORCYCLE;
    }
}
