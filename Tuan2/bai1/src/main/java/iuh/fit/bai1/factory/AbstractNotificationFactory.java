package iuh.fit.bai1.factory;

import iuh.fit.bai1.service.NotificationService;

/**
 * Abstract Factory Pattern - Abstract factory cho notification services
 * Định nghĩa interface chung cho việc tạo family của notification services
 */
public abstract class AbstractNotificationFactory {
    
    /**
     * Abstract method để tạo notification service
     * @param type loại notification service
     * @return NotificationService instance
     */
    public abstract NotificationService createNotificationService(String type);
    
    /**
     * Abstract method để tạo batch notification service (gửi hàng loạt)
     * @param type loại notification service
     * @return NotificationService instance cho batch processing
     */
    public abstract NotificationService createBatchNotificationService(String type);
    
    /**
     * Factory method để lấy factory instance dựa trên environment
     * @param environment môi trường (PRODUCTION, DEVELOPMENT, TEST)
     * @return AbstractNotificationFactory instance
     */
    public static AbstractNotificationFactory getFactory(String environment) {
        switch (environment.toUpperCase()) {
            case "PRODUCTION":
                return new ProductionNotificationFactory();
            case "DEVELOPMENT":
                return new DevelopmentNotificationFactory();
            case "TEST":
                return new TestNotificationFactory();
            default:
                throw new IllegalArgumentException("Unsupported environment: " + environment);
        }
    }
}