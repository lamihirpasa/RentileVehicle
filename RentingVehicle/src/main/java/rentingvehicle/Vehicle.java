package rentingvehicle;
public class Vehicle {
    private String type;
    private String registrationNumber;
    private double basePrice;

    public Vehicle(String type, String registrationNumber, double basePrice) {
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.basePrice = basePrice;
    }

    public String getDetails() {
        return "Type: " + type + ", Registration: " + registrationNumber + ", Base Price: $" + basePrice;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return type + "," + registrationNumber + "," + basePrice;
    }

    public static Vehicle fromString(String vehicleData) {
        String[] parts = vehicleData.split(",");
        return new Vehicle(parts[0], parts[1], Double.parseDouble(parts[2]));
    }
}
