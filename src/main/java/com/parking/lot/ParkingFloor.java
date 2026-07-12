package com.parking.lot;

import com.parking.lot.enums.SpotType;
import com.parking.lot.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingFloor {
    private List<ParkingSpot> parkingSpotList = new ArrayList<>();

    ParkingFloor(int num, int motorcycle, int compact, int large) {
        for (int i = 0; i < motorcycle; i++) {
            parkingSpotList.add(new ParkingSpot("F-" + num + "M-" + i, SpotType.MOTORCYCLE));
        }
        for (int i = 0; i < compact; i++) {
            parkingSpotList.add(new ParkingSpot("F-" + num + "C-" + i, SpotType.COMPACT));
        }
        for (int i = 0; i < large; i++) {
            parkingSpotList.add(new ParkingSpot("F-" + num + "L-" + i, SpotType.LARGE));
        }
    }

    Optional<ParkingSpot> findSpot(Vehicle vehicle) {
        return parkingSpotList.stream().filter(s -> s.canFit(vehicle)).findFirst();
    }
}
