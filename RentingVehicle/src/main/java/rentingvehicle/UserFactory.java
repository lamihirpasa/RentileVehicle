
package rentingvehicle;
public class UserFactory {
    public static User createUser(String role) throws IllegalArgumentException {
        switch (role.toLowerCase()) {
            case "admin":
                return new Admin();
            case "customer":
                return new Customer();
            case "owner":
                return new Owner();
            default:
                throw new IllegalArgumentException("Invalid user role: " + role);
        }
    }
}
