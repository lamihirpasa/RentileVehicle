package rentingvehicle;
import java.io.*;
import java.util.*;

public class VehicleRentalSystem {
    // List to store all vehicles, currently static and accessible by all methods in the class
    static List<Vehicle> vehicleList = new ArrayList<>();
    private static final String VEHICLE_FILE = "vehicles.txt"; // File path for vehicle data

    public static void main(String[] args) {
        // Start the scanner to accept user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Vehicle Rental System!");
        boolean systemRunning = true;

        while (systemRunning) {
            // Display role options to the user
            System.out.println("\nPlease choose your role:");
            System.out.println("[1] Admin\n[2] Customer\n[3] Owner\n[4] Exit");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                User user = null; // User object to perform role-based operations

                // Create the user based on role choice using Factory Pattern
                switch (choice) {
                    case 1:
                        user = UserFactory.createUser("admin");
                        break;
                    case 2:
                        user = UserFactory.createUser("customer");
                        break;
                    case 3:
                        user = UserFactory.createUser("owner");
                        break;
                    case 4:
                        systemRunning = false; // Exit the loop and terminate the program
                        continue;
                    default:
                        System.out.println("Invalid choice.");
                        continue;
                }

                // If a user is created successfully, perform role-related actions
                if (user != null) {
                    user.performRole(scanner); // Polymorphism in action (different user roles)
                }
            } catch (Exception e) {
                // Handle invalid input gracefully
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        // Exit message when the system terminates
        System.out.println("Thank you for using the Vehicle Rental System. Goodbye!");
        scanner.close();
    }

    // Loads vehicle data from the file and adds them to the vehicleList
    public static void loadVehiclesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(VEHICLE_FILE))) {
            String line;
            // Read each line and convert it into a Vehicle object
            while ((line = reader.readLine()) != null) {
                vehicleList.add(Vehicle.fromString(line)); // Assuming fromString is a static method in Vehicle class
            }
        } catch (IOException e) {
            System.out.println("No existing vehicle data found. Starting fresh.");
        }
    }

    // Saves all vehicle data to a file
    public static void saveVehiclesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VEHICLE_FILE))) {
            // Write each vehicle object to the file, assuming toString method provides a proper string representation
            for (Vehicle vehicle : vehicleList) {
                writer.write(vehicle.toString());
                writer.newLine(); // Add a new line after each vehicle
            }
        } catch (IOException e) {
            System.out.println("Error saving vehicles to file: " + e.getMessage());
        }
    }
}
