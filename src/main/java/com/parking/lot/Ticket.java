package com.parking.lot;

import com.parking.lot.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Ticket {
    final String ticketId = UUID.randomUUID().toString();
    public final Vehicle vehicle;
    final ParkingSpot parkingSpot;
    LocalDateTime entryTime = LocalDateTime.now();
    LocalDateTime exitTime;

    public Ticket(Vehicle vehicle, ParkingSpot parkingSpot) {
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
    }

    public long getParkingHours(){
        long mins = ChronoUnit.MINUTES.between(
                entryTime,
                exitTime!=null ? exitTime: LocalDateTime.now());
        return Math.max(1, (mins + 59) / 60);
    }
}
