package rentingvehicle;

import java.util.Scanner;

public class Owner implements User {
    @Override
    public void performRole(Scanner scanner) {
        System.out.println("Owner: Adding vehicles to the system.");
        boolean running = true;

        while (running) {
            System.out.println("Choose an action: [1] Add Vehicle [2] View Owned Vehicles [3] Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addVehicle(scanner);
                    break;
                case 2:
                    viewOwnedVehicles();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addVehicle(Scanner scanner) {
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();
        System.out.print("Enter registration number: ");
        String registration = scanner.nextLine();
        System.out.print("Enter base price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Vehicle vehicle = new Vehicle(type, registration, price);
        VehicleData.addVehicle(vehicle);
        System.out.println("Vehicle added: " + vehicle.getDetails());
    }

    private void viewOwnedVehicles() {
        System.out.println("Owned Vehicles:");
        for (Vehicle vehicle : VehicleData.getAllVehicles()) {
            System.out.println(vehicle.getDetails());
        }
    }
}
