package iuh.fit.faulttolerance.service;

import iuh.fit.faulttolerance.model.User;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ServiceBClient {
    
    private static final Logger logger = LoggerFactory.getLogger(ServiceBClient.class);
    private static final String SERVICE_NAME = "serviceB";
    
    private final RestTemplate restTemplate;
    private final String serviceBUrl;
    
    public ServiceBClient(@Value("${service-b.url}") String serviceBUrl) {
        this.serviceBUrl = serviceBUrl;
        this.restTemplate = new RestTemplate();
    }

    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "getUsersFallback")
    @Retry(name = SERVICE_NAME)
    @RateLimiter(name = SERVICE_NAME)
    @Bulkhead(name = SERVICE_NAME)
    @TimeLimiter(name = SERVICE_NAME)
    public CompletableFuture<List<User>> getUsers() {
        logger.info("Calling Service B to get users");
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<List<User>> response = restTemplate.exchange(
                    serviceBUrl + "/api/users",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<User>>() {}
                );
                return response.getBody();
            } catch (Exception e) {
                logger.error("Error calling Service B: {}", e.getMessage());
                throw new RuntimeException("Service B is unavailable", e);
            }
        });
    }

    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "getUserByIdFallback")
    @Retry(name = SERVICE_NAME)
    @RateLimiter(name = SERVICE_NAME)
    @Bulkhead(name = SERVICE_NAME)
    @TimeLimiter(name = SERVICE_NAME)
    public CompletableFuture<User> getUserById(Long id) {
        logger.info("Calling Service B to get user with id: {}", id);
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<User> response = restTemplate.getForEntity(
                    serviceBUrl + "/api/users/" + id,
                    User.class
                );
                return response.getBody();
            } catch (Exception e) {
                logger.error("Error calling Service B for user {}: {}", id, e.getMessage());
                throw new RuntimeException("Service B is unavailable", e);
            }
        });
    }

    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "createUserFallback")
    @Retry(name = SERVICE_NAME)
    @RateLimiter(name = SERVICE_NAME)
    @Bulkhead(name = SERVICE_NAME)
    @TimeLimiter(name = SERVICE_NAME)
    public CompletableFuture<User> createUser(User user) {
        logger.info("Calling Service B to create user: {}", user.getName());
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<User> response = restTemplate.postForEntity(
                    serviceBUrl + "/api/users",
                    user,
                    User.class
                );
                return response.getBody();
            } catch (Exception e) {
                logger.error("Error creating user in Service B: {}", e.getMessage());
                throw new RuntimeException("Service B is unavailable", e);
            }
        });
    }

    // Fallback methods
    public CompletableFuture<List<User>> getUsersFallback(Exception ex) {
        logger.warn("Fallback: Getting users from cache/default data. Reason: {}", ex.getMessage());
        
        List<User> fallbackUsers = Arrays.asList(
            new User(1L, "Fallback User 1", "fallback1@example.com", "CACHED"),
            new User(2L, "Fallback User 2", "fallback2@example.com", "CACHED")
        );
        
        return CompletableFuture.completedFuture(fallbackUsers);
    }

    public CompletableFuture<User> getUserByIdFallback(Long id, Exception ex) {
        logger.warn("Fallback: Getting user {} from cache. Reason: {}", id, ex.getMessage());
        
        User fallbackUser = new User(id, "Fallback User " + id, "fallback" + id + "@example.com", "CACHED");
        return CompletableFuture.completedFuture(fallbackUser);
    }

    public CompletableFuture<User> createUserFallback(User user, Exception ex) {
        logger.warn("Fallback: User creation failed, returning cached response. Reason: {}", ex.getMessage());
        
        user.setId(999L);
        user.setStatus("FALLBACK_CREATED");
        return CompletableFuture.completedFuture(user);
    }
}