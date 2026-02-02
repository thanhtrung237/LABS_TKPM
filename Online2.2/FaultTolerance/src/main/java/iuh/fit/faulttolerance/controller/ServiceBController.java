package iuh.fit.faulttolerance.controller;

import iuh.fit.faulttolerance.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This controller simulates Service B (external service)
 * It includes intentional failures to test fault tolerance patterns
 */
@RestController
@RequestMapping("/api/users")
public class ServiceBController {
    
    private static final Logger logger = LoggerFactory.getLogger(ServiceBController.class);
    private final AtomicLong counter = new AtomicLong();
    private final Random random = new Random();
    
    // Simulate some users data
    private final List<User> users = Arrays.asList(
        new User(1L, "John Doe", "john@example.com", "ACTIVE"),
        new User(2L, "Jane Smith", "jane@example.com", "ACTIVE"),
        new User(3L, "Bob Johnson", "bob@example.com", "INACTIVE"),
        new User(4L, "Alice Brown", "alice@example.com", "ACTIVE"),
        new User(5L, "Charlie Wilson", "charlie@example.com", "PENDING")
    );

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() throws InterruptedException {
        long requestCount = counter.incrementAndGet();
        logger.info("Service B: Getting all users (request #{})", requestCount);
        
        // Simulate random failures (30% chance)
        if (random.nextDouble() < 0.3) {
            logger.error("Service B: Simulated failure for request #{}", requestCount);
            throw new RuntimeException("Service B is temporarily unavailable");
        }
        
        // Simulate random delays
        if (random.nextDouble() < 0.2) {
            logger.warn("Service B: Simulating slow response for request #{}", requestCount);
            Thread.sleep(4000); // 4 seconds delay
        }
        
        logger.info("Service B: Successfully returning {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws InterruptedException {
        long requestCount = counter.incrementAndGet();
        logger.info("Service B: Getting user by id {} (request #{})", id, requestCount);
        
        // Simulate random failures (25% chance)
        if (random.nextDouble() < 0.25) {
            logger.error("Service B: Simulated failure for user {} request #{}", id, requestCount);
            throw new RuntimeException("Service B database connection failed");
        }
        
        // Simulate random delays
        if (random.nextDouble() < 0.15) {
            logger.warn("Service B: Simulating slow response for user {} request #{}", id, requestCount);
            Thread.sleep(3500); // 3.5 seconds delay
        }
        
        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (user != null) {
            logger.info("Service B: Found user: {}", user.getName());
            return ResponseEntity.ok(user);
        } else {
            logger.warn("Service B: User not found: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) throws InterruptedException {
        long requestCount = counter.incrementAndGet();
        logger.info("Service B: Creating user {} (request #{})", user.getName(), requestCount);
        
        // Simulate random failures (20% chance)
        if (random.nextDouble() < 0.2) {
            logger.error("Service B: Simulated creation failure for user {} request #{}", user.getName(), requestCount);
            throw new RuntimeException("Service B validation failed");
        }
        
        // Simulate processing delay
        if (random.nextDouble() < 0.1) {
            logger.warn("Service B: Simulating slow user creation for {} request #{}", user.getName(), requestCount);
            Thread.sleep(2500); // 2.5 seconds delay
        }
        
        // Simulate user creation
        user.setId(100L + requestCount);
        user.setStatus("ACTIVE");
        
        logger.info("Service B: Successfully created user with id: {}", user.getId());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service B is running");
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getStats() {
        return ResponseEntity.ok("Service B - Total requests: " + counter.get());
    }
}