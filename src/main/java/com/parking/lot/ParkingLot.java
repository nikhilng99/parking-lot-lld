package com.parking.lot;

import com.parking.lot.exception.InvalidTicketException;
import com.parking.lot.exception.ParkingFullException;
import com.parking.lot.pricing.HourlyPricing;
import com.parking.lot.pricing.PricingStrategy;
import com.parking.lot.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private static class Holder {
        static final ParkingLot INSTANCE = new ParkingLot();
    }
    public static ParkingLot getInstance() { return Holder.INSTANCE; }
    private ParkingLot() {}

    private List<ParkingFloor> parkingFloors = new ArrayList<>();
    private final Map<String, Ticket> activeTickets = new HashMap<>();
    private PricingStrategy pricing = new HourlyPricing();

    public void addFloor(ParkingFloor f) { parkingFloors.add(f); }
    public void setPricing(PricingStrategy p) { pricing = p; }

    synchronized public Ticket park(Vehicle vehicle) {
        for (ParkingFloor floor : parkingFloors) {
            Optional<ParkingSpot> spot = floor.findSpot(vehicle);
            if (spot.isPresent()) {
                spot.get().park(vehicle);
                Ticket t = new Ticket(vehicle, spot.get());
                activeTickets.put(t.ticketId, t);
                return t;
            }
        }
        throw new ParkingFullException("Parking full for: " + vehicle.getType());
    }

    synchronized public double exit(String ticketId){
        Ticket t = activeTickets.remove(ticketId);
        if(t==null) throw new InvalidTicketException("Invalid ticket");
        t.exitTime = LocalDateTime.now();
        t.parkingSpot.free();
        return pricing.calculateFee(t);
    }
}
