package rentingvehicle;

import java.util.Scanner;

// Owner class implements User interface
// OOP Principle: Polymorphism
// Allows the Owner class to define its specific behavior for the `performRole` method.
public class Owner implements User {

    @Override
    public void performRole(Scanner scanner) {
        System.out.println("Owner: Providing vehicles for rental.");
        boolean running = true;

        // Menu-driven interaction for the Owner
        while (running) {
            System.out.println("Choose an action: \n[1] Add Vehicle \n[2] Exit");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        addVehicle(scanner); // Encapsulates vehicle addition logic
                        break;
                    case 2:
                        running = false; // Exits the loop
                        break;
                    default:
                        System.out.println("Invalid choice."); // Input validation
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number."); // Robustness: Error handling
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    // Adds a vehicle to the rental system
    // OOP Principle: Encapsulation
    // Hides the logic for creating and adding a vehicle, keeping it self-contained.
    private void addVehicle(Scanner scanner) {
        try {
            System.out.print("Enter vehicle type: "); // Input vehicle type
            String type = scanner.nextLine();

            System.out.print("Enter registration number: "); // Input registration number
            String registration = scanner.nextLine();

            System.out.print("Enter base price per day: "); // Input base price
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            // Creates a new Vehicle object
            Vehicle vehicle = new Vehicle(type, registration, price);

            // Adds the vehicle to the global list and persists the data
            VehicleRentalSystem.vehicleList.add(vehicle);
            VehicleRentalSystem.saveVehiclesToFile(); // Saves vehicles to a file for persistence

            System.out.println("Vehicle added: " + vehicle.getDetails());
        } catch (Exception e) {
            System.out.println("Error adding vehicle. Please check your input."); // Robustness: Handles invalid inputs
        }
    }
}
