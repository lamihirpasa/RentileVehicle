package rentingvehicle;

public class Rental {
    private Vehicle vehicle;
    private int days;

    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }

    public double calculatePrice() {
        return vehicle.getBasePrice() * days;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getDetails() {
        return vehicle.getDetails() + ", Days: " + days + ", Total Price: $" + calculatePrice();
    }

    // Convert rental to file string
    public String toFileString() {
        return vehicle.toString() + "," + days;
    }

    // Create rental from file string
    public static Rental fromFileString(String rentalData) {
        String[] parts = rentalData.split(",");
        Vehicle vehicle = new Vehicle(parts[0], parts[1], Double.parseDouble(parts[2]));
        int days = Integer.parseInt(parts[3]);
        return new Rental(vehicle, days);
    }
}
