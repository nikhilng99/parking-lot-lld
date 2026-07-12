# Parking Lot — Low Level Design (LLD)

A production-style implementation of a multi-floor parking lot system demonstrating core Object-Oriented Design principles and Design Patterns in Java.

---

## Problem Statement

Design a parking lot system that supports:
- Multiple floors with different parking spot types
- Vehicles of different types (Car, SUV, Motorcycle, Truck)
- Automatic spot assignment based on vehicle type
- Ticket generation on entry and fee calculation on exit
- Thread-safe concurrent vehicle entry and exit

---

## Design Patterns Used

### 1. Singleton — ParkingLot
There is exactly one parking lot managing all shared state — available spots and active tickets. Multiple instances would give inconsistent views of availability.

Used Bill Pugh static inner class approach: lazy initialization, thread-safe by JVM class loading guarantee, zero synchronization overhead.

```java
private static class Holder {
    static final ParkingLot INSTANCE = new ParkingLot();
}
public static ParkingLot getInstance() { return Holder.INSTANCE; }
```

### 2. Strategy — PricingStrategy
Fee calculation varies independently of parking logic. New pricing models (flat rate, monthly pass, peak hour) can be added without modifying any existing class.

Open-Closed Principle: closed for modification, open for extension via new strategy implementations.

```java
public interface PricingStrategy {
    double calculateFee(Ticket ticket);
}
// Swap at runtime: parkingLot.setPricing(new FlatRatePricing(100.0));
```

### 3. Template Method — Vehicle (Abstract Class)
All vehicles share common state (licensePlate, vehicleType) but each type knows what spot size it needs. Abstract class defines that every vehicle must answer `getRequiredSpotType()`, subclasses define how.

---

## Class Structure

```
ParkingLot (Singleton)
    └── ParkingFloor (1..*)
            └── ParkingSpot (1..*)
                    └── Vehicle (0..1)

Ticket → references Vehicle + ParkingSpot
PricingStrategy → used by ParkingLot on exit
```

---

## Package Structure

```
com.parking.lot
├── ParkingLot.java          # Singleton orchestrator
├── ParkingFloor.java        # Floor with list of spots
├── ParkingSpot.java         # Individual spot
├── Ticket.java              # Issued on entry
├── Main.java                # Demo
├── enums/
│   ├── SpotType.java        # MOTORCYCLE, COMPACT, LARGE
│   └── VehicleType.java     # CAR, MOTORCYCLE, SUV, TRUCK
├── vehicle/
│   ├── Vehicle.java         # Abstract base
│   ├── Car.java
│   ├── MotorCycle.java
│   ├── Suv.java
│   └── Truck.java
├── pricing/
│   ├── PricingStrategy.java # Interface
│   └── HourlyPricing.java  # Rs 20/hr bike, 50 car, 80 SUV, 120 truck
└── exception/
    ├── ParkingFullException.java
    └── InvalidTicketException.java
```

---

## How It Works

### Entry Flow
```
Vehicle arrives
    → ParkingLot.park(vehicle)
    → Searches floors in order (ground floor first)
    → ParkingFloor.findSpot(vehicle)
    → ParkingSpot.canFit(vehicle) checks type match + availability
    → First available spot found → vehicle parked
    → Ticket issued with UUID, vehicle reference, spot reference, entry time
    → Ticket stored in activeTickets map
```

### Exit Flow
```
Vehicle exits with ticket ID
    → ParkingLot.exit(ticketId)
    → Ticket removed from activeTickets
    → Exit time recorded on ticket
    → Spot freed (parkedVehicle = null)
    → Fee calculated via PricingStrategy
    → Fee returned
```

---

## Concurrency Handling

`park()` and `exit()` are both `synchronized` on the ParkingLot instance.

Since ParkingLot is a Singleton (one instance per JVM), `synchronized` on a non-static method effectively acts as a global lock. Only one thread executes park or exit at a time — no two vehicles can be assigned the same spot concurrently.

For distributed systems with multiple application servers, the next step would be database-level row locking (`SELECT FOR UPDATE`) to prevent double-booking across server boundaries.

---

## Hourly Pricing Rates

| Vehicle Type | Rate per Hour |
|---|---|
| Motorcycle | ₹20 |
| Car | ₹50 |
| SUV | ₹80 |
| Truck | ₹120 |

Minimum charge: 1 hour. Partial hours rounded up (61 minutes = 2 hours).

---

## Extending the System

**Adding a new vehicle type (e.g., Bus):**
- Add `BUS` to `VehicleType` enum
- Create `Bus.java` extending `Vehicle`, return `SpotType.LARGE`
- Zero other changes

**Adding a new pricing model (e.g., flat rate):**
- Create `FlatRatePricing.java` implementing `PricingStrategy`
- Call `parkingLot.setPricing(new FlatRatePricing(100.0))`
- Zero other changes

**Adding a new spot type (e.g., disability spots):**
- Add `DISABILITY` to `SpotType` enum
- Add disability spots in `ParkingFloor` constructor
- Add disability check in `canFit()`
- Zero changes to `ParkingLot` or `Ticket`

---

## Running the Demo

```bash
# Clone and run Main.java
# Demo parks Car, Car, Motorcycle, Truck
# Waits 3 seconds to simulate parking duration
# All four vehicles exit with calculated fees
```

---

## Key Design Decisions

**Why Singleton for ParkingLot?**
Shared mutable state — available spots and active tickets — must be consistent across the entire system. Multiple instances would give conflicting views of availability.

**Why Strategy for pricing?**
Pricing changes frequently and independently of parking mechanics. Strategy allows adding or swapping pricing logic at runtime without modifying the core parking lot code. This is the Open-Closed Principle.

**Why abstract class for Vehicle instead of interface?**
Vehicles share concrete state (licensePlate, vehicleType) and behaviour. An interface cannot hold state. Abstract class captures the shared state while forcing subclasses to implement `getRequiredSpotType()`.

**Why custom exceptions over RuntimeException?**
`ParkingFullException` and `InvalidTicketException` make error scenarios explicit and self-documenting. Callers can catch specific exceptions if needed without being forced to (they extend RuntimeException).
