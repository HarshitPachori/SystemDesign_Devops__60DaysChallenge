import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A simple in-memory Key-Value store implementation.
 * This class demonstrates basic put and get operations for string keys and string values.
 * It uses a HashMap for storage and a ReadWriteLock for basic thread-safety.
 *
 * This is a highly simplified version and does not include:
 * - Persistence (data is lost when the application stops)
 * - Replication or High Availability
 * - Distributed nature (it's a single-node store)
 * - Eviction policies (e.g., LRU, LFU)
 * - Advanced features like TTL, transactions, or complex data types.
 */


/**
 * Although we have used HashMap here which is not thread safe , 
 * that's why we are using external synchronization using ReentrantReadWriteLock and ReadWrite Lock
 * 
 * We can also use ConcurrentHashMap which is thread safe, and then we don't have to use external locks there.
 * 
 */
public class SimpleKeyValueStore {

    // The underlying storage for key-value pairs.
    // Using HashMap for O(1) average time complexity for put/get.
    private final Map<String, String> store;

    // A ReadWriteLock to manage concurrent access to the store.
    // Read operations (get) can happen concurrently.
    // Write operations (put) require exclusive access.
    private final ReadWriteLock lock;

    /**
     * Constructor to initialize the key-value store.
     */
    public SimpleKeyValueStore() {
        this.store = new HashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * Stores a key-value pair in the store.
     * If the key already exists, its value will be updated.
     *
     * @param key The string key to store.
     * @param value The string value associated with the key.
     */
    public void put(String key, String value) {
        // Acquire a write lock to ensure exclusive access during modification.
        lock.writeLock().lock();
        try {
            store.put(key, value);
            System.out.println("PUT: Key '" + key + "', Value '" + value + "' stored.");
        } finally {
            // Always release the write lock in a finally block to prevent deadlocks.
            lock.writeLock().unlock();
        }
    }

    /**
     * Retrieves the value associated with a given key from the store.
     *
     * @param key The string key to retrieve.
     * @return The string value associated with the key, or null if the key is not found.
     */
    public String get(String key) {
        // Acquire a read lock to allow concurrent reads.
        lock.readLock().lock();
        try {
            String value = store.get(key);
            if (value != null) {
                System.out.println("GET: Key '" + key + "', Value '" + value + "' retrieved.");
            } else {
                System.out.println("GET: Key '" + key + "' not found.");
            }
            return value;
        } finally {
            // Always release the read lock.
            lock.readLock().unlock();
        }
    }

    /**
     * Main method to demonstrate the usage of the SimpleKeyValueStore.
     */
    public static void main(String[] args) {
        SimpleKeyValueStore kvStore = new SimpleKeyValueStore();

        // Demonstrate PUT operations
        kvStore.put("user:1", "Alice");
        kvStore.put("product:101", "Laptop");
        kvStore.put("user:2", "Bob");

        // Demonstrate GET operations
        String user1Name = kvStore.get("user:1");
        String productPrice = kvStore.get("product:101");
        String nonExistentKey = kvStore.get("order:500");

        // Update a value
        kvStore.put("user:1", "Alice Smith");
        String updatedUser1Name = kvStore.get("user:1");

        // Demonstrate concurrent access (simplified, in a real scenario you'd use multiple threads)
        System.out.println("\n--- Simulating Concurrent Access (conceptual) ---");
        // In a real multi-threaded environment, these would run in separate threads.
        // The ReadWriteLock ensures correct behavior.
        new Thread(() -> kvStore.put("session:abc", "data1")).start();
        new Thread(() -> kvStore.get("product:101")).start();
        new Thread(() -> kvStore.put("session:xyz", "data2")).start();
        new Thread(() -> kvStore.get("session:abc")).start();

        // Give a moment for threads to execute (not robust for real concurrency testing)
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("--- Simulation End ---");
    }
}
