package rentingvehicle;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Customer implements User {
    private List<Rental> rentedVehicles = new ArrayList<>();
    private static final String RENTED_VEHICLES_FILE = "rentedVehicles.txt";

    public Customer() {
        loadRentedVehiclesFromFile();
    }

    @Override
    public void performRole(Scanner scanner) {
        System.out.println("Customer: Booking vehicles.");
        boolean running = true;
        while (running) {
            System.out.println("Choose an action: [1] View Available Vehicles [2] Book Vehicle [3] Return Vehicle [4] View Rentals [5] Exit");
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
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    private void viewAvailableVehicles() {
        System.out.println("Available vehicles:");
        for (Vehicle v : VehicleRentalSystem.vehicleList) {
            System.out.println(v.getDetails());
        }
    }

    private void bookVehicle(Scanner scanner) {
        try {
            System.out.print("Enter registration number of the vehicle to book: ");
            String regNumber = scanner.nextLine();
            Vehicle toBook = VehicleRentalSystem.vehicleList.stream()
                    .filter(v -> v.getRegistrationNumber().equalsIgnoreCase(regNumber))
                    .findFirst()
                    .orElse(null);
            if (toBook != null) {
                System.out.print("Enter number of days to rent: ");
                int days = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                Rental rental = new Rental(toBook, days);
                rentedVehicles.add(rental);
                VehicleRentalSystem.vehicleList.remove(toBook);
                VehicleRentalSystem.saveVehiclesToFile();
                saveRentedVehiclesToFile();
                System.out.println("Vehicle booked: " + toBook.getDetails());
            } else {
                System.out.println("Vehicle not available!");
            }
        } catch (Exception e) {
            System.out.println("Error booking vehicle. Please check your input.");
        }
    }

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
                Rental returnedRental = rentedVehicles.remove(index);
                VehicleRentalSystem.vehicleList.add(returnedRental.getVehicle());
                VehicleRentalSystem.saveVehiclesToFile();
                saveRentedVehiclesToFile();
                double totalCost = returnedRental.calculatePrice();
                System.out.println("Vehicle returned: " + returnedRental.getVehicle().getDetails());
                System.out.println("Total rental cost: $" + totalCost);
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (Exception e) {
            System.out.println("Error returning vehicle. Please check your input.");
        }
    }

    private void viewRentals() {
        System.out.println("Your current rentals:");
        for (Rental rental : rentedVehicles) {
            System.out.println(rental.getDetails());
        }
    }

    // Save rented vehicles to file
    private void saveRentedVehiclesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RENTED_VEHICLES_FILE))) {
            for (Rental rental : rentedVehicles) {
                writer.write(rental.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving rented vehicles to file: " + e.getMessage());
        }
    }

    // Load rented vehicles from file
    private void loadRentedVehiclesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RENTED_VEHICLES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Rental rental = Rental.fromFileString(line);
                rentedVehicles.add(rental);
            }
        } catch (IOException e) {
            System.out.println("No existing rental data found. Starting fresh.");
        }
    }
}
