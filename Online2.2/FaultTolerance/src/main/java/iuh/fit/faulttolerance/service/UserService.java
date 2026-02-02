package iuh.fit.faulttolerance.service;

import iuh.fit.faulttolerance.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private ServiceBClient serviceBClient;

    public CompletableFuture<List<User>> getAllUsers() {
        logger.info("UserService: Getting all users");
        return serviceBClient.getUsers();
    }

    public CompletableFuture<User> getUserById(Long id) {
        logger.info("UserService: Getting user by id: {}", id);
        return serviceBClient.getUserById(id);
    }

    public CompletableFuture<User> createUser(User user) {
        logger.info("UserService: Creating new user: {}", user.getName());
        return serviceBClient.createUser(user);
    }

    public CompletableFuture<String> processUserData(Long userId) {
        logger.info("UserService: Processing user data for user: {}", userId);
        
        return getUserById(userId)
                .thenApply(user -> {
                    if (user != null) {
                        // Simulate some business logic processing
                        String result = "Processed user: " + user.getName() + 
                                       " with status: " + user.getStatus();
                        logger.info("Processing completed: {}", result);
                        return result;
                    } else {
                        logger.warn("User not found for processing: {}", userId);
                        return "User not found for processing";
                    }
                })
                .exceptionally(throwable -> {
                    logger.error("Error processing user data: {}", throwable.getMessage());
                    return "Error processing user data: " + throwable.getMessage();
                });
    }
}