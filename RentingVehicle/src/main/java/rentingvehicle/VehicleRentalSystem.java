package rentingvehicle;
import java.io.*;
import java.util.*;

public class VehicleRentalSystem {
    static List<Vehicle> vehicleList = new ArrayList<>();
    private static final String VEHICLE_FILE = "vehicles.txt";

    public static void main(String[] args) {
        //loadVehiclesFromFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Vehicle Rental System!");
        boolean systemRunning = true;

        while (systemRunning) {
            System.out.println("\nPlease choose your role:");
            System.out.println("[1] Admin\n[2] Customer\n[3] Owner\n[4] Exit");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                User user = null;

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
                        systemRunning = false;
                        continue;
                    default:
                        System.out.println("Invalid choice.");
                        continue;
                }

                if (user != null) {
                    user.performRole(scanner);
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        System.out.println("Thank you for using the Vehicle Rental System. Goodbye!");
        scanner.close();
    }

    public static void loadVehiclesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(VEHICLE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                vehicleList.add(Vehicle.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("No existing vehicle data found. Starting fresh.");
        }
    }

    public static void saveVehiclesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VEHICLE_FILE))) {
            for (Vehicle vehicle : vehicleList) {
                writer.write(vehicle.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving vehicles to file: " + e.getMessage());
        }
    }
}
