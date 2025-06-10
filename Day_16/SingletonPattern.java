import java.io.Serializable;
import java.lang.reflect.Constructor;

/**
 * Singleton pattern is used when we need to ensure that a class has only one
 * instance and that instance is globally accessible throughout the application.
 *
 * This implementation demonstrates:
 * 1. Lazy initialization (instance created only when needed).
 * 2. Thread safety using Double-Checked Locking.
 * 3. Protection against Reflection API attacks.
 * 4. Prevention against breaking by serialization (via readResolve()).
 */
class Singleton implements Serializable {

    // volatile keyword ensures that changes to the instance variable are immediately
    // visible to other threads. This is crucial for Double-Checked Locking to work
    // correctly, preventing issues like instruction reordering.
    private static volatile Singleton instance;

    // A flag to ensure the constructor is called only once,
    // primarily to protect against reflection API.
    private static boolean constructorCalled = false;

    /**
     * Private constructor to prevent direct instantiation from outside the class.
     * It also contains a check to prevent instantiation via Reflection API
     * after the first legitimate instance has been created.
     */
    private Singleton() {
        // Protection against Reflection API:
        // If an instance already exists, throw a RuntimeException to prevent
        // breaking the singleton guarantee.
        if (constructorCalled) {
             throw new RuntimeException("Attempt to create a second instance of Singleton. " +
                                        "This class is a Singleton. Use getInstance() method.");
        }
        constructorCalled = true; // Mark constructor as called
        System.out.println("Singleton instance created (constructor called).");
    }

    /**
     * Provides the global point of access to the single instance of the Singleton class.
     * Implements Double-Checked Locking for thread-safe lazy initialization.
     *
     * @return The single instance of the Singleton class.
     */
    public static Singleton getInstance() {
        // First check: If an instance already exists, return it immediately without locking.
        if (instance == null) {
            // Synchronized block to ensure only one thread can create the instance.
            // Locking on the class object prevents multiple threads from entering
            // this critical section simultaneously.
            synchronized (Singleton.class) {
                // Second check: After acquiring the lock, check again if the instance is null.
                // This is crucial to prevent multiple instances if multiple threads pass
                // the first null check simultaneously before the lock is acquired.
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    /**
     * This method is automatically called by the deserialization process.
     * It ensures that when a serialized Singleton object is deserialized,
     * the existing single instance is returned instead of creating a new one.
     * This protects against breaking the Singleton pattern through serialization/deserialization.
     *
     * @return The existing single instance of the Singleton class.
     */
    protected Object readResolve() {
        System.out.println("readResolve() called: Returning existing Singleton instance.");
        return instance;
    }

    // Example method to demonstrate functionality
    public void showMessage() {
        System.out.println("Hello from the Singleton instance!");
    }
}

/**
 * This class demonstrates how to use the Singleton pattern
 * and attempts to break it using Reflection API.
 */
class SingletonPattern {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Singleton Pattern Demo ---");

        // 1. Get the instance via the standard getInstance() method
        Singleton s1 = Singleton.getInstance();
        s1.showMessage();
        System.out.println("Hashcode of s1: " + s1.hashCode());

        // 2. Try to get another instance (should be the same)
        Singleton s2 = Singleton.getInstance();
        System.out.println("Hashcode of s2: " + s2.hashCode());
        System.out.println("s1 and s2 are the same instance: " + (s1 == s2));

        // 3. Attempt to break Singleton using Reflection API
        System.out.println("\n--- Attempting to break Singleton using Reflection ---");
        Singleton s3 = null;
        try {
            // Get the private constructor
            Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
            // Make it accessible (bypass private access modifier)
            constructor.setAccessible(true);
            // Create a new instance
            s3 = constructor.newInstance();
            System.out.println("Hashcode of s3 (via Reflection): " + s3.hashCode());
            System.out.println("s1 and s3 are the same instance: " + (s1 == s3));
        } catch (RuntimeException e) {
            System.out.println("Reflection attempt failed as expected: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during reflection attempt: " + e.getMessage());
        }

        // 4. (Optional) Demonstrate breaking by Serialization (uncomment to test)
        // This requires writing and reading objects from a file
        // import java.io.*;
        /*
        System.out.println("\n--- Attempting to break Singleton using Serialization ---");
        Singleton s4 = null;
        try {
            // Serialize s1
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream("singleton.ser"));
            out.writeObject(s1);
            out.close();
            System.out.println("Singleton instance s1 serialized to singleton.ser");

            // Deserialize to create a new instance
            ObjectInput in = new ObjectInputStream(new FileInputStream("singleton.ser"));
            s4 = (Singleton) in.readObject();
            in.close();
            System.out.println("Singleton instance s4 deserialized from singleton.ser");

            System.out.println("Hashcode of s4 (via Serialization): " + s4.hashCode());
            System.out.println("s1 and s4 are the same instance: " + (s1 == s4)); // Should be true if readResolve() is implemented
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Serialization/Deserialization attempt failed: " + e.getMessage());
        }
        */
    }
}


/**
 * The most robust way to implement a Singleton in Java is often using an Enum.
 * This approach inherently handles serialization, reflection, and thread safety.
 */
enum EnumSingleton {
    INSTANCE; // The single instance

    // You can add methods and fields as needed
    public void showMessage() {
        System.out.println("Hello from the Enum Singleton instance!");
    }

    // Enum singletons are implicitly Serializable and handle reflection correctly.
    // No need for readResolve() or special constructor checks.
}

class EnumSingletonDemo {
    public static void main(String[] args) {
        System.out.println("\n--- Enum Singleton Demo ---");

        EnumSingleton es1 = EnumSingleton.INSTANCE;
        es1.showMessage();
        System.out.println("Hashcode of es1: " + es1.hashCode());

        EnumSingleton es2 = EnumSingleton.INSTANCE;
        System.out.println("Hashcode of es2: " + es2.hashCode());
        System.out.println("es1 and es2 are the same instance: " + (es1 == es2));
    }
}
