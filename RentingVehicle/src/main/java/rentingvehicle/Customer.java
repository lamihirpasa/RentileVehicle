package rentingvehicle;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Customer implements User {
    // OOP Principle: Encapsulation
    // The `rentedVehicles` list is private to the Customer class, ensuring that other parts of the program cannot directly modify it.
    private List<Rental> rentedVehicles = new ArrayList<>();
    private static final String RENTED_VEHICLES_FILE = "rentedVehicles.txt";

    // Constructor: Loads any existing rentals from a file
    // OOP Principle: Encapsulation
    public Customer() {
        loadRentedVehiclesFromFile();
    }

    // OOP Principle: Polymorphism
    // The `performRole` method is overridden to implement the Customer's specific behavior.
    @Override
    public void performRole(Scanner scanner) {
        System.out.println("Customer: Booking vehicles.");
        boolean running = true;

        // Menu-driven system
        while (running) {
            System.out.println("Choose an action: \n[1] View Available Vehicles \n[2] Book Vehicle \n[3] Return Vehicle \n[4] View Rentals \n[5] Exit");
            try {
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
                        returnVehicle(scanner);
                        break;
                    case 4:
                        viewRentals();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice."); // Input validation for robustness
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number."); // Exception handling for robustness
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    // Method to view available vehicles
    // OOP Principle: Abstraction
    // Hides the internal details of the vehicle storage mechanism.
    private void viewAvailableVehicles() {
        System.out.println("Available vehicles:");
        for (Vehicle v : VehicleRentalSystem.vehicleList) {
            System.out.println(v.getDetails()); // Delegates detail formatting to the Vehicle class
        }
    }

    // Method to book a vehicle
    // Design Pattern: Encapsulation
    // Ensures that only valid vehicles are booked and handles all booking details internally.
    private void bookVehicle(Scanner scanner) {
        try {
            System.out.print("Enter registration number of the vehicle to book: ");
            String regNumber = scanner.nextLine();

            // Design Pattern: Stream API (Functional Programming)
            Vehicle toBook = VehicleRentalSystem.vehicleList.stream()
                    .filter(v -> v.getRegistrationNumber().equalsIgnoreCase(regNumber)) // Filters for matching vehicle
                    .findFirst() // Finds the first match
                    .orElse(null); // Returns null if no match is found

            if (toBook != null) {
                System.out.print("Enter number of days to rent: ");
                int days = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Encapsulation: Rental object represents a booking
                Rental rental = new Rental(toBook, days);

                rentedVehicles.add(rental); // Add rental to the customer's rented vehicles
                VehicleRentalSystem.vehicleList.remove(toBook); // Remove from available vehicles
                VehicleRentalSystem.saveVehiclesToFile(); // Persist changes
                saveRentedVehiclesToFile(); // Save rented vehicles
                System.out.println("Vehicle booked: " + toBook.getDetails());
            } else {
                System.out.println("Vehicle not available!");
            }
        } catch (Exception e) {
            System.out.println("Error booking vehicle. Please check your input."); // Error handling for robustness
        }
    }

    // Method to return a rented vehicle
    // OOP Principle: Encapsulation
    // Handles the details of returning a vehicle and calculating the rental cost.
    private void returnVehicle(Scanner scanner) {
        try {
            System.out.println("Your rented vehicles:");
            for (int i = 0; i < rentedVehicles.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + rentedVehicles.get(i).getDetails());
            }
            System.out.print("Select a vehicle to return: ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (index >= 0 && index < rentedVehicles.size()) {
                Rental returnedRental = rentedVehicles.remove(index); // Remove from customer's rentals
                VehicleRentalSystem.vehicleList.add(returnedRental.getVehicle()); // Add back to available vehicles
                VehicleRentalSystem.saveVehiclesToFile();
                saveRentedVehiclesToFile();
                double totalCost = returnedRental.calculatePrice(); // Calculate cost
                System.out.println("Vehicle returned: " + returnedRental.getVehicle().getDetails());
                System.out.println("Total rental cost: $" + totalCost);
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (Exception e) {
            System.out.println("Error returning vehicle. Please check your input.");
        }
    }

    // Method to view customer's current rentals
    // Design Pattern: Encapsulation
    private void viewRentals() {
        System.out.println("Your current rentals:");
        for (Rental rental : rentedVehicles) {
            System.out.println(rental.getDetails()); // Delegates detail formatting to the Rental class
        }
    }

    // Save rented vehicles to a file
    // OOP Principle: Persistence/Encapsulation
    private void saveRentedVehiclesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RENTED_VEHICLES_FILE))) {
            for (Rental rental : rentedVehicles) {
                writer.write(rental.toFileString()); // Serializes rental data for persistence
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving rented vehicles to file: " + e.getMessage());
        }
    }

    // Load rented vehicles from a file
    // OOP Principle: Persistence/Encapsulation
    private void loadRentedVehiclesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RENTED_VEHICLES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Rental rental = Rental.fromFileString(line); // Deserializes rental data
                rentedVehicles.add(rental);
            }
        } catch (IOException e) {
            System.out.println("No existing rental data found. Starting fresh."); // Handles the case of a missing file
        }
    }
}
