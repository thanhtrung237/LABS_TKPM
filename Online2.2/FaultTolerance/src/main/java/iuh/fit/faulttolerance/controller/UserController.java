package iuh.fit.faulttolerance.controller;

import iuh.fit.faulttolerance.model.User;
import iuh.fit.faulttolerance.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/service-a")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public CompletableFuture<ResponseEntity<List<User>>> getAllUsers() {
        logger.info("REST: Getting all users");
        
        return userService.getAllUsers()
                .thenApply(users -> {
                    logger.info("REST: Retrieved {} users", users.size());
                    return ResponseEntity.ok(users);
                })
                .exceptionally(throwable -> {
                    logger.error("REST: Error getting users: {}", throwable.getMessage());
                    return ResponseEntity.internalServerError().build();
                });
    }

    @GetMapping("/users/{id}")
    public CompletableFuture<ResponseEntity<User>> getUserById(@PathVariable Long id) {
        logger.info("REST: Getting user by id: {}", id);
        
        return userService.getUserById(id)
                .thenApply(user -> {
                    if (user != null) {
                        logger.info("REST: Found user: {}", user.getName());
                        return ResponseEntity.ok(user);
                    } else {
                        logger.warn("REST: User not found: {}", id);
                        return ResponseEntity.<User>notFound().build();
                    }
                })
                .exceptionally(throwable -> {
                    logger.error("REST: Error getting user {}: {}", id, throwable.getMessage());
                    return ResponseEntity.<User>internalServerError().build();
                });
    }

    @PostMapping("/users")
    public CompletableFuture<ResponseEntity<User>> createUser(@RequestBody User user) {
        logger.info("REST: Creating user: {}", user.getName());
        
        return userService.createUser(user)
                .thenApply(createdUser -> {
                    logger.info("REST: Created user with id: {}", createdUser.getId());
                    return ResponseEntity.ok(createdUser);
                })
                .exceptionally(throwable -> {
                    logger.error("REST: Error creating user: {}", throwable.getMessage());
                    return ResponseEntity.internalServerError().build();
                });
    }

    @GetMapping("/process/{userId}")
    public CompletableFuture<ResponseEntity<String>> processUserData(@PathVariable Long userId) {
        logger.info("REST: Processing user data for: {}", userId);
        
        return userService.processUserData(userId)
                .thenApply(result -> {
                    logger.info("REST: Processing completed: {}", result);
                    return ResponseEntity.ok(result);
                })
                .exceptionally(throwable -> {
                    logger.error("REST: Error processing user data: {}", throwable.getMessage());
                    return ResponseEntity.internalServerError().body("Processing failed: " + throwable.getMessage());
                });
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service A is running");
    }
}