package rentingvehicle;

import java.util.Scanner;

// Base User Interface
public interface User {
    /**
     * Defines the role-specific behavior for a user.
     * 
     * @param scanner A Scanner instance for user input.
     * 
     * Design Principle: Interface Segregation
     * - The interface provides a specific method for defining role-based behavior.
     * 
     * OOP Principle: Abstraction
     * - Concrete implementations define the specific behavior for each user role.
     */
    void performRole(Scanner scanner);
}
