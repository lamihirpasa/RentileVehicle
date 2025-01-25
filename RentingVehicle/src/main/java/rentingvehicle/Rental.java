package rentingvehicle;

public class Rental {
    private Vehicle vehicle; // Composition: Rental contains a Vehicle
    private int days; // Number of days the vehicle is rented for

    // Constructor to create a Rental object
    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }

    // Calculates the total price of the rental
    // OOP Principle: Encapsulation
    // The price calculation is encapsulated within this method and hidden from external classes.
    public double calculatePrice() {
        return vehicle.getBasePrice() * days;
    }

    // Getter for the associated vehicle
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Returns details of the rental
    // Includes vehicle details, number of days, and total price
    public String getDetails() {
        return vehicle.getDetails() + ", Days: " + days + ", Total Price: $" + calculatePrice();
    }

    // Converts the rental data to a comma-separated string for file storage
    // Design Pattern: Serialization (manual implementation)
    public String toFileString() {
        return vehicle.toString() + "," + days;
    }

    // Reconstructs a Rental object from a stored string
    // Design Pattern: Factory Method
    // Creates a Rental object by parsing the string representation of its data
    public static Rental fromFileString(String rentalData) {
        String[] parts = rentalData.split(","); // Splits data by commas
        Vehicle vehicle = new Vehicle(parts[0], parts[1], Double.parseDouble(parts[2])); // Create Vehicle
        int days = Integer.parseInt(parts[3]); // Parse number of rental days
        return new Rental(vehicle, days); // Return reconstructed Rental object
    }
}
