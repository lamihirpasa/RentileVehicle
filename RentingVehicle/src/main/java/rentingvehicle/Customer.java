package rentingvehicle;

import java.util.Scanner;

public class Customer implements User {
    @Override
    public void performRole(Scanner scanner) {
        System.out.println("Customer: Booking and returning vehicles.");
        boolean running = true;

        while (running) {
            System.out.println("Choose an action: [1] View Available Vehicles [2] Book Vehicle [3] View Bookings [4] Return Vehicle [5] Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    viewAvailableVehicles();
                    break;
                case 2:
                    bookVehicle(scanner);
                    break;
                case 3:
                    viewBookedVehicles();
                    break;
                case 4:
                    returnVehicle(scanner);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAvailableVehicles() {
        System.out.println("Available Vehicles:");
        for (Vehicle vehicle : VehicleData.getAvailableVehicles()) {
            System.out.println(vehicle.getDetails());
        }
    }

    private void bookVehicle(Scanner scanner) {
        System.out.print("Enter registration number of the vehicle to book: ");
        String regNumber = scanner.nextLine();
        boolean success = VehicleData.bookVehicle(regNumber);

        if (success) {
            System.out.println("Vehicle booked successfully.");
        } else {
            System.out.println("Vehicle not found or already booked.");
        }
    }

    private void viewBookedVehicles() {
        System.out.println("Your Booked Vehicles:");
        for (Vehicle vehicle : VehicleData.getBookedVehicles()) {
            System.out.println(vehicle.getDetails());
        }
    }

    private void returnVehicle(Scanner scanner) {
        System.out.print("Enter registration number of the vehicle to return: ");
        String regNumber = scanner.nextLine();

        boolean success = VehicleData.returnBookedVehicle(regNumber);
        if (success) {
            System.out.println("Vehicle returned successfully.");
        } else {
            System.out.println("You have not booked a vehicle with this registration number.");
        }
    }
}
