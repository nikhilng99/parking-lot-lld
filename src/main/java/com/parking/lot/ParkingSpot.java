package com.parking.lot;

import com.parking.lot.enums.SpotType;
import com.parking.lot.vehicle.Vehicle;

public class ParkingSpot {
    private String spotId;
    private SpotType spotType;
    private Vehicle parkedVehicle;

    public ParkingSpot(String spotId, SpotType spotType) {
        this.spotId = spotId;
        this.spotType = spotType;
    }

    boolean canFit(Vehicle vehicle){
        return parkedVehicle == null && spotType == vehicle.getRequiredSpotType();
    }

    void free(){ parkedVehicle = null;}
    void park(Vehicle v) { parkedVehicle = v; }
}
