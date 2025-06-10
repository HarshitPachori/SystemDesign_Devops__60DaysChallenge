package design_patterns.factory_pattern;

/**
 * Factory pattern is used when we have to create all the objects of the
 * subclasses of a superclass (or implementors of an interface)
 * by passing a single value only.
 *
 * It focuses on creating objects rather than exposing the instantiation logic
 * directly to the client. This provides robust code and loose coupling.
 * Loose coupling means that if we change the string argument at runtime,
 * our object will change automatically, without modifying the client code.
 */

// 1. Product Interface (or Abstract Class)
public interface Employee {
    int salary();
}

// 2. Concrete Products
class WebDev implements Employee {
    @Override
    public int salary() {
        System.out.println("Calculating Web developer salary...");
        return 40000;
    }
}

class AndroidDev implements Employee {
    @Override
    public int salary() {
        System.out.println("Calculating Android developer salary...");
        return 50000;
    }
}

// 3. Simple Factory Class
 class EmployeeFactory {
    /**
     * This static method acts as the factory method.
     * It takes an employee type string and returns an instance of the
     * corresponding concrete Employee class.
     *
     * @param empType A string representing the type of employee to create.
     * @return An instance of Employee (WebDev, AndroidDev, etc.), or null if type is unknown.
     */
    public static Employee getEmployee(String empType) {
        if (empType == null || empType.trim().isEmpty()) {
            return null; // Handle null or empty input gracefully
        }

        String normalizedEmpType = empType.trim().toUpperCase();

        if (normalizedEmpType.equals("ANDROID DEVELOPER")) {
            return new AndroidDev();
        } else if (normalizedEmpType.equals("WEB DEVELOPER")) {
            return new WebDev();
        } else {
            // Optional: throw an IllegalArgumentException for unknown types
            // throw new IllegalArgumentException("Unknown employee type: " + empType);
            System.out.println("Unknown employee type: " + empType);
            return null; // Return null for unknown types
        }
    }
}

/**
 * This class demonstrates the usage of the EmployeeFactory.
 * The client code interacts with the factory to get Employee objects
 * without knowing the concrete implementation details.
 */
public class FactoryPattern {
    public static void main(String[] args) {
        System.out.println("--- Factory Pattern Demo ---");

        // Client requests an Android Developer
        Employee androidDeveloper = EmployeeFactory.getEmployee("ANDROID DEVELOPER");
        if (androidDeveloper != null) {
            System.out.println("Android Developer created. Salary: " + androidDeveloper.salary());
        }

        System.out.println("--------------------");

        // Client requests a Web Developer
        Employee webDeveloper = EmployeeFactory.getEmployee("WEB DEVELOPER");
        if (webDeveloper != null) {
            System.out.println("Web Developer created. Salary: " + webDeveloper.salary());
        }

        System.out.println("--------------------");

        // Client requests an unknown employee type
        Employee qaEngineer = EmployeeFactory.getEmployee("QA ENGINEER");
        if (qaEngineer == null) {
            System.out.println("QA Engineer could not be created as type is unknown.");
        }
        System.out.println("--------------------");

        // Demonstrate how to handle different cases
        Employee emptyType = EmployeeFactory.getEmployee("");
        if (emptyType == null) {
            System.out.println("Empty type input handled.");
        }
    }
}
