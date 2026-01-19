package iuh.fit.bai1.controller;

import iuh.fit.bai1.config.NotificationConfigManager;
import iuh.fit.bai1.demo.InteractiveDemo;
import iuh.fit.bai1.factory.AbstractNotificationFactory;
import iuh.fit.bai1.factory.NotificationServiceFactory;
import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller để demo các Design Patterns qua REST API
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {
    
    private final NotificationServiceFactory factory;
    private final InteractiveDemo interactiveDemo;
    
    @Autowired
    public DemoController(NotificationServiceFactory factory, InteractiveDemo interactiveDemo) {
        this.factory = factory;
        this.interactiveDemo = interactiveDemo;
    }
    
    /**
     * Demo Singleton Pattern
     */
    @GetMapping("/singleton")
    public ResponseEntity<Map<String, Object>> demonstrateSingleton() {
        Map<String, Object> response = new HashMap<>();
        
        // Tạo hai instances
        NotificationConfigManager config1 = NotificationConfigManager.getInstance();
        NotificationConfigManager config2 = NotificationConfigManager.getInstance();
        
        // Kiểm tra
        boolean sameInstance = (config1 == config2);
        int hash1 = config1.hashCode();
        int hash2 = config2.hashCode();
        
        // Test shared state
        config1.setConfiguration("demo.singleton.test", "singleton_value_" + System.currentTimeMillis());
        String sharedValue = config2.getConfiguration("demo.singleton.test");
        
        response.put("pattern", "Singleton Pattern");
        response.put("sameInstance", sameInstance);
        response.put("hash1", hash1);
        response.put("hash2", hash2);
        response.put("sharedState", sharedValue);
        response.put("explanation", "Singleton ensures only one instance exists and state is shared");
        response.put("success", sameInstance);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Demo Factory Method Pattern
     */
    @GetMapping("/factory-method/{type}")
    public ResponseEntity<Map<String, Object>> demonstrateFactoryMethod(@PathVariable String type) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Sử dụng Factory để tạo service
            NotificationService service = factory.createNotificationService(type);
            
            // Tạo demo message
            NotificationMessage message = new NotificationMessage(
                "factory@demo.com",
                "Factory Method Demo",
                "This service was created using Factory Method Pattern",
                "MEDIUM"
            );
            
            // Gửi notification
            boolean result = service.sendNotification(message);
            
            response.put("pattern", "Factory Method Pattern");
            response.put("requestedType", type);
            response.put("createdServiceType", service.getServiceType());
            response.put("serviceClass", service.getClass().getSimpleName());
            response.put("isAvailable", service.isAvailable());
            response.put("notificationSent", result);
            response.put("supportedTypes", factory.getSupportedTypes());
            response.put("explanation", "Factory Method creates objects without specifying exact classes");
            response.put("success", true);
            
        } catch (IllegalArgumentException e) {
            response.put("pattern", "Factory Method Pattern");
            response.put("error", e.getMessage());
            response.put("supportedTypes", factory.getSupportedTypes());
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Demo Abstract Factory Pattern
     */
    @GetMapping("/abstract-factory/{environment}/{type}")
    public ResponseEntity<Map<String, Object>> demonstrateAbstractFactory(
            @PathVariable String environment, 
            @PathVariable String type) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Lấy factory cho environment
            AbstractNotificationFactory abstractFactory = AbstractNotificationFactory.getFactory(environment);
            
            // Tạo regular service
            NotificationService regularService = abstractFactory.createNotificationService(type);
            
            // Tạo batch service
            NotificationService batchService = abstractFactory.createBatchNotificationService(type);
            
            // Demo message
            NotificationMessage message = new NotificationMessage(
                "abstract@demo.com",
                "Abstract Factory Demo",
                "Services created for " + environment + " environment",
                "HIGH"
            );
            
            // Gửi notifications
            boolean regularResult = regularService.sendNotification(message);
            boolean batchResult = batchService.sendNotification(message);
            
            response.put("pattern", "Abstract Factory Pattern");
            response.put("environment", environment);
            response.put("requestedType", type);
            response.put("regularService", Map.of(
                "type", regularService.getServiceType(),
                "class", regularService.getClass().getSimpleName(),
                "sent", regularResult
            ));
            response.put("batchService", Map.of(
                "type", batchService.getServiceType(),
                "class", batchService.getClass().getSimpleName(),
                "sent", batchResult
            ));
            response.put("explanation", "Abstract Factory creates families of related objects for specific environments");
            response.put("success", true);
            
        } catch (IllegalArgumentException e) {
            response.put("pattern", "Abstract Factory Pattern");
            response.put("error", e.getMessage());
            response.put("supportedEnvironments", new String[]{"PRODUCTION", "DEVELOPMENT", "TEST"});
            response.put("success", false);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * So sánh tất cả patterns
     */
    @GetMapping("/compare-patterns")
    public ResponseEntity<Map<String, Object>> comparePatterns() {
        Map<String, Object> response = new HashMap<>();
        
        // Singleton demo
        NotificationConfigManager config1 = NotificationConfigManager.getInstance();
        NotificationConfigManager config2 = NotificationConfigManager.getInstance();
        
        // Factory Method demo
        NotificationService emailService = factory.createNotificationService("EMAIL");
        
        // Abstract Factory demo
        AbstractNotificationFactory prodFactory = AbstractNotificationFactory.getFactory("PRODUCTION");
        AbstractNotificationFactory devFactory = AbstractNotificationFactory.getFactory("DEVELOPMENT");
        NotificationService prodService = prodFactory.createNotificationService("EMAIL");
        NotificationService devService = devFactory.createNotificationService("EMAIL");
        
        response.put("singleton", Map.of(
            "description", "Ensures only one instance exists",
            "sameInstance", config1 == config2,
            "useCase", "Configuration management, resource sharing"
        ));
        
        response.put("factoryMethod", Map.of(
            "description", "Creates objects without specifying exact classes",
            "createdService", emailService.getClass().getSimpleName(),
            "serviceType", emailService.getServiceType(),
            "useCase", "Creating different types of services dynamically"
        ));
        
        response.put("abstractFactory", Map.of(
            "description", "Creates families of related objects",
            "productionService", prodService.getClass().getSimpleName(),
            "developmentService", devService.getClass().getSimpleName(),
            "useCase", "Environment-specific object creation"
        ));
        
        response.put("summary", "All three patterns work together to create a flexible, maintainable notification system");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Performance test cho Singleton
     */
    @GetMapping("/performance-test")
    public ResponseEntity<Map<String, Object>> performanceTest() {
        Map<String, Object> response = new HashMap<>();
        
        // Test Singleton performance
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            NotificationConfigManager.getInstance();
        }
        long endTime = System.nanoTime();
        
        long duration = endTime - startTime;
        double milliseconds = duration / 1_000_000.0;
        
        // Memory efficiency test
        NotificationConfigManager[] instances = new NotificationConfigManager[100];
        for (int i = 0; i < 100; i++) {
            instances[i] = NotificationConfigManager.getInstance();
        }
        
        boolean allSame = true;
        for (int i = 1; i < 100; i++) {
            if (instances[0] != instances[i]) {
                allSame = false;
                break;
            }
        }
        
        response.put("test", "Singleton Performance Test");
        response.put("iterations", 10000);
        response.put("totalTimeMs", milliseconds);
        response.put("averageTimeNs", duration / 10000.0);
        response.put("memoryEfficient", allSame);
        response.put("explanation", "Singleton pattern provides excellent performance and memory efficiency");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Lấy thông tin tổng quan về hệ thống
     */
    @GetMapping("/system-overview")
    public ResponseEntity<Map<String, Object>> getSystemOverview() {
        Map<String, Object> response = new HashMap<>();
        
        NotificationConfigManager config = NotificationConfigManager.getInstance();
        
        response.put("designPatterns", Map.of(
            "singleton", "NotificationConfigManager - Configuration management",
            "factoryMethod", "NotificationServiceFactory - Service creation",
            "abstractFactory", "Environment-specific factories"
        ));
        
        response.put("supportedNotificationTypes", factory.getSupportedTypes());
        response.put("supportedEnvironments", new String[]{"PRODUCTION", "DEVELOPMENT", "TEST"});
        response.put("serviceStatuses", config.getServiceStatuses());
        response.put("configurationCount", config.getAllConfigurations().size());
        
        response.put("endpoints", Map.of(
            "singleton", "/api/demo/singleton",
            "factoryMethod", "/api/demo/factory-method/{type}",
            "abstractFactory", "/api/demo/abstract-factory/{environment}/{type}",
            "comparison", "/api/demo/compare-patterns",
            "performance", "/api/demo/performance-test"
        ));
        
        return ResponseEntity.ok(response);
    }
}