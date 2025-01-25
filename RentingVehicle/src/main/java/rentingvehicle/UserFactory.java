package rentingvehicle;

/**
 * Factory class for creating user objects based on their role.
 * 
 * Design Pattern: Factory
 * - Centralizes object creation logic to promote flexibility and maintainability.
 */
public class UserFactory {

    /**
     * Creates a user object based on the provided role.
     *
     * @param role The role of the user ("admin", "customer", "owner").
     * @return A User object corresponding to the specified role.
     * @throws IllegalArgumentException If the role is invalid or unrecognized.
     *
     * Design Principle: Open-Closed Principle
     * - New roles can be added with minimal changes to this method.
     */
    public static User createUser(String role) throws IllegalArgumentException {
        switch (role.toLowerCase()) { // Case-insensitive role matching
            case "admin":
                return new Admin();
            case "customer":
                return new Customer();
            case "owner":
                return new Owner();
            default:
                // Fail fast for invalid roles
                throw new IllegalArgumentException("Invalid user role: " + role);
        }
    }
}
