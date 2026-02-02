package iuh.fit.faulttolerance.controller;

import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fault-tolerance")
public class FaultToleranceTestController {

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;
    
    @Autowired
    private RetryRegistry retryRegistry;
    
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry;
    
    @Autowired
    private BulkheadRegistry bulkheadRegistry;

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getFaultToleranceStatus() {
        Map<String, Object> status = new HashMap<>();
        
        // Circuit Breaker Status
        circuitBreakerRegistry.getAllCircuitBreakers().forEach(cb -> {
            Map<String, Object> cbStatus = new HashMap<>();
            cbStatus.put("state", cb.getState().toString());
            cbStatus.put("failureRate", cb.getMetrics().getFailureRate());
            cbStatus.put("numberOfCalls", cb.getMetrics().getNumberOfCalls());
            cbStatus.put("numberOfFailedCalls", cb.getMetrics().getNumberOfFailedCalls());
            cbStatus.put("numberOfSuccessfulCalls", cb.getMetrics().getNumberOfSuccessfulCalls());
            status.put("circuitBreaker_" + cb.getName(), cbStatus);
        });
        
        // Rate Limiter Status
        rateLimiterRegistry.getAllRateLimiters().forEach(rl -> {
            Map<String, Object> rlStatus = new HashMap<>();
            rlStatus.put("availablePermissions", rl.getMetrics().getAvailablePermissions());
            rlStatus.put("numberOfWaitingThreads", rl.getMetrics().getNumberOfWaitingThreads());
            status.put("rateLimiter_" + rl.getName(), rlStatus);
        });
        
        // Bulkhead Status
        bulkheadRegistry.getAllBulkheads().forEach(bh -> {
            Map<String, Object> bhStatus = new HashMap<>();
            bhStatus.put("availableConcurrentCalls", bh.getMetrics().getAvailableConcurrentCalls());
            bhStatus.put("maxAllowedConcurrentCalls", bh.getMetrics().getMaxAllowedConcurrentCalls());
            status.put("bulkhead_" + bh.getName(), bhStatus);
        });
        
        // Retry Status
        retryRegistry.getAllRetries().forEach(retry -> {
            Map<String, Object> retryStatus = new HashMap<>();
            retryStatus.put("numberOfFailedCallsWithRetryAttempt", 
                retry.getMetrics().getNumberOfFailedCallsWithRetryAttempt());
            retryStatus.put("numberOfFailedCallsWithoutRetryAttempt", 
                retry.getMetrics().getNumberOfFailedCallsWithoutRetryAttempt());
            retryStatus.put("numberOfSuccessfulCallsWithRetryAttempt", 
                retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt());
            retryStatus.put("numberOfSuccessfulCallsWithoutRetryAttempt", 
                retry.getMetrics().getNumberOfSuccessfulCallsWithoutRetryAttempt());
            status.put("retry_" + retry.getName(), retryStatus);
        });
        
        return ResponseEntity.ok(status);
    }

    @GetMapping("/reset")
    public ResponseEntity<String> resetCircuitBreakers() {
        circuitBreakerRegistry.getAllCircuitBreakers().forEach(cb -> {
            cb.transitionToClosedState();
        });
        return ResponseEntity.ok("All circuit breakers have been reset to CLOSED state");
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("description", "Fault Tolerance Demo with Resilience4J");
        info.put("patterns", "Circuit Breaker, Retry, Rate Limiter, Bulkhead, Time Limiter");
        info.put("serviceA", "http://localhost:8080/api/service-a");
        info.put("serviceB", "http://localhost:8081/api/users (simulated)");
        info.put("monitoring", "http://localhost:8080/actuator");
        info.put("status", "http://localhost:8080/api/fault-tolerance/status");
        return ResponseEntity.ok(info);
    }
}