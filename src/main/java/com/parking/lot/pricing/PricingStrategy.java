package com.parking.lot.pricing;

import com.parking.lot.Ticket;

public interface PricingStrategy {
    double calculateFee(Ticket t);
}
