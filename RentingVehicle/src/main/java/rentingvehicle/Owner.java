package rentingvehicle;
import java.util.Scanner;

public class Owner implements User {
    @Override
    public void performRole(Scanner scanner) {
        System.out.println("Owner: Providing vehicles for rental.");
        boolean running = true;
        while (running) {
            System.out.println("Choose an action: [1] Add Vehicle [2] Exit");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        addVehicle(scanner);
                        break;
                    case 2:
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

    private void addVehicle(Scanner scanner) {
        try {
            System.out.print("Enter vehicle type: ");
            String type = scanner.nextLine();
            System.out.print("Enter registration number: ");
            String registration = scanner.nextLine();
            System.out.print("Enter base price per day: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            Vehicle vehicle = new Vehicle(type, registration, price);
            VehicleRentalSystem.vehicleList.add(vehicle);
            VehicleRentalSystem.saveVehiclesToFile();
            System.out.println("Vehicle added: " + vehicle.getDetails());
        } catch (Exception e) {
            System.out.println("Error adding vehicle. Please check your input.");
        }
    }
}
