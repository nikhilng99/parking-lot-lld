package com.parking.lot.pricing;

import com.parking.lot.Ticket;
import com.parking.lot.enums.VehicleType;

import java.util.Map;

public class HourlyPricing implements PricingStrategy{
    final Map<VehicleType, Double> rates = Map.of(
            VehicleType.MOTORCYCLE, 20.0,
            VehicleType.CAR, 50.0,
            VehicleType.SUV, 80.0,
            VehicleType.TRUCK, 120.0);

    @Override
    public double calculateFee(Ticket ticket) {
        return rates.getOrDefault(ticket.vehicle.getVehicleType(),50.0) * ticket.getParkingHours();
    }
}
