package rentingvehicle;

import java.util.Scanner;

// Admin class implements the User interface
public class Admin implements User {

    // OOP Principle: Polymorphism
    // The `performRole` method is overridden to provide Admin-specific functionality.
    @Override
    public void performRole(Scanner scanner) {
        System.out.println("Admin: Managing vehicles.");
        boolean running = true;
        
        while (running) {
            System.out.println("Choose an action: \n[1] Add Vehicle \n[2] View Vehicles \n[3] Exit");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        addVehicle(scanner); // Delegates task to a helper method for modularity (SRP)
                        break;
                    case 2:
                        viewVehicles(); // Delegates task to a helper method for modularity (SRP)
                        break;
                    case 3:
                        running = false; // Exits the loop
                        break;
                    default:
                        System.out.println("Invalid choice."); // Input validation for robustness
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number."); // Input error handling
                scanner.nextLine(); // Consume invalid input to prevent infinite loop
            }
        }
    }

    // Helper method to add a vehicle
    // Design Pattern: Encapsulation
    // This method hides the complexity of vehicle creation from other parts of the class.
    private void addVehicle(Scanner scanner) {
        try {
            System.out.print("Enter vehicle type: ");
            String type = scanner.nextLine(); // Accepts the type of the vehicle
            System.out.print("Enter registration number: ");
            String registration = scanner.nextLine(); // Accepts the registration number
            System.out.print("Enter base price per day: ");
            double price = scanner.nextDouble(); // Accepts the price of the vehicle
            scanner.nextLine(); // Consume newline

            // Object creation using constructor
            Vehicle vehicle = new Vehicle(type, registration, price); // Encapsulation

            // Accessing a shared resource
            // Design Pattern: Singleton (VehicleRentalSystem is acting as a central manager)
            VehicleRentalSystem.vehicleList.add(vehicle); // Adds the vehicle to the global vehicle list
            
            VehicleRentalSystem.saveVehiclesToFile(); // Saves vehicle data persistently
            
            // Displays confirmation
            System.out.println("Vehicle added: " + vehicle.getDetails()); // Encapsulation of details in the Vehicle class
        } catch (Exception e) {
            System.out.println("Error adding vehicle. Please check your input."); // Exception handling for robustness
        }
    }

    // Helper method to view all vehicles
    // OOP Principle: Encapsulation
    // This method provides controlled access to the vehicle list without exposing its internal details.
    private void viewVehicles() {
        System.out.println("Vehicles in system:");

        // Iterating over vehicles
        // OOP Principle: Abstraction
        // Details of how vehicles are stored are hidden from the caller; only the interface is exposed.
        for (Vehicle v : VehicleRentalSystem.vehicleList) {
            System.out.println(v.getDetails()); // Delegates the responsibility of formatting to the `Vehicle` class
        }
    }
}
