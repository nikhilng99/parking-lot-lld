package com.parking.lot;

import com.parking.lot.vehicle.Car;
import com.parking.lot.vehicle.MotorCycle;
import com.parking.lot.vehicle.Truck;
import com.parking.lot.vehicle.Vehicle;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Get Parking Lot instance
        ParkingLot parkingLot = ParkingLot.getInstance();

        // Add Floors
        parkingLot.addFloor(new ParkingFloor(
                1,      // Floor Number
                2,      // Motorcycle Spots
                3,      // Compact Spots
                2       // Large Spots
        ));

        parkingLot.addFloor(new ParkingFloor(
                2,
                2,
                3,
                2
        ));

        // Create Vehicles
        Vehicle car1 = new Car("KA01AB1234");
        Vehicle car2 = new Car("KA02CD5678");
        Vehicle bike1 = new MotorCycle("TN02CD5675");
        Vehicle truck1 = new Truck("MH02CD5678");

        // Park Vehicle 1
        Ticket ticket1 = parkingLot.park(car1);
        System.out.println("Car 1 Parked");
        System.out.println("Ticket Id : " + ticket1.ticketId);

        // Park Vehicle 2
        Ticket ticket2 = parkingLot.park(car2);
        System.out.println("Car 2 Parked");
        System.out.println("Ticket Id : " + ticket2.ticketId);

        // Park Bike 1
        Ticket ticket3 = parkingLot.park(bike1);
        System.out.println("Bike 1 Parked");
        System.out.println("Ticket Id : " + ticket3.ticketId);

        // Park Truck 1
        Ticket ticket4 = parkingLot.park(truck1);
        System.out.println("Truck 1 Parked");
        System.out.println("Ticket Id : " + ticket4.ticketId);

        // Wait for a few seconds (just for demo)
        Thread.sleep(3000);

        // Exit Vehicle 1
        double fee1 = parkingLot.exit(ticket1.ticketId);
        System.out.println("\nCar 1 Exited");
        System.out.println("Fee : ₹" + fee1);

        // Exit Vehicle 2
        double fee2 = parkingLot.exit(ticket2.ticketId);
        System.out.println("\nCar 2 Exited");
        System.out.println("Fee : ₹" + fee2);

        // Exit Bike 1
        double fee3 = parkingLot.exit(ticket3.ticketId);
        System.out.println("\nBike 1 Exited");
        System.out.println("Fee : ₹" + fee3);

        // Exit Truck 1
        double fee4 = parkingLot.exit(ticket4.ticketId);
        System.out.println("\nTruck 1 Exited");
        System.out.println("Fee : ₹" + fee4);
    }
}