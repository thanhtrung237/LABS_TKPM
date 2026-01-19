package iuh.fit.bai1.service;

import iuh.fit.bai1.config.NotificationConfigManager;
import iuh.fit.bai1.factory.AbstractNotificationFactory;
import iuh.fit.bai1.factory.NotificationServiceFactory;
import iuh.fit.bai1.model.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service quản lý toàn bộ hệ thống notification
 * Sử dụng cả Factory Method và Abstract Factory patterns
 */
@Service
public class NotificationManager {
    
    private final NotificationServiceFactory serviceFactory;
    private final NotificationConfigManager configManager;
    
    @Value("${app.environment:DEVELOPMENT}")
    private String environment;
    
    @Autowired
    public NotificationManager(NotificationServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        this.configManager = NotificationConfigManager.getInstance();
    }
    
    /**
     * Gửi notification sử dụng Factory Method Pattern
     */
    public boolean sendNotification(String type, NotificationMessage message) {
        try {
            NotificationService service = serviceFactory.createNotificationService(type);
            return service.sendNotification(message);
        } catch (Exception e) {
            System.err.println("❌ Error sending notification: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gửi notification sử dụng Abstract Factory Pattern
     */
    public boolean sendNotificationWithEnvironment(String type, NotificationMessage message) {
        try {
            AbstractNotificationFactory factory = AbstractNotificationFactory.getFactory(environment);
            NotificationService service = factory.createNotificationService(type);
            return service.sendNotification(message);
        } catch (Exception e) {
            System.err.println("❌ Error sending notification with environment factory: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gửi batch notification sử dụng Abstract Factory Pattern
     */
    public boolean sendBatchNotification(String type, NotificationMessage message) {
        try {
            AbstractNotificationFactory factory = AbstractNotificationFactory.getFactory(environment);
            NotificationService service = factory.createBatchNotificationService(type);
            return service.sendNotification(message);
        } catch (Exception e) {
            System.err.println("❌ Error sending batch notification: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gửi notification đến nhiều kênh cùng lúc
     */
    public List<Boolean> sendMultiChannelNotification(NotificationMessage message, String... types) {
        List<Boolean> results = new ArrayList<>();
        
        System.out.println("📢 Sending multi-channel notification...");
        
        for (String type : types) {
            if (serviceFactory.isTypeSupported(type)) {
                boolean result = sendNotification(type, message);
                results.add(result);
                System.out.println("   " + type + ": " + (result ? "✅ Success" : "❌ Failed"));
            } else {
                System.out.println("   " + type + ": ❌ Unsupported type");
                results.add(false);
            }
        }
        
        return results;
    }
    
    /**
     * Gửi notification với fallback mechanism
     */
    public boolean sendNotificationWithFallback(NotificationMessage message, String primaryType, String fallbackType) {
        System.out.println("📢 Sending notification with fallback...");
        
        // Thử gửi qua kênh chính
        boolean primaryResult = sendNotification(primaryType, message);
        if (primaryResult) {
            System.out.println("✅ Primary channel (" + primaryType + ") succeeded");
            return true;
        }
        
        // Nếu thất bại, thử kênh dự phòng
        System.out.println("⚠️ Primary channel failed, trying fallback...");
        boolean fallbackResult = sendNotification(fallbackType, message);
        
        if (fallbackResult) {
            System.out.println("✅ Fallback channel (" + fallbackType + ") succeeded");
        } else {
            System.out.println("❌ Both primary and fallback channels failed");
        }
        
        return fallbackResult;
    }
    
    /**
     * Lấy thông tin cấu hình hệ thống
     */
    public void displaySystemConfiguration() {
        System.out.println("\n=== NOTIFICATION SYSTEM CONFIGURATION ===");
        System.out.println("Environment: " + environment);
        System.out.println("Supported Types: " + String.join(", ", serviceFactory.getSupportedTypes()));
        
        System.out.println("\nService Status:");
        configManager.getServiceStatuses().forEach((service, status) -> 
            System.out.println("  " + service + ": " + (status ? "✅ Enabled" : "❌ Disabled"))
        );
        
        System.out.println("\nConfigurations:");
        configManager.getAllConfigurations().forEach((key, value) -> 
            System.out.println("  " + key + ": " + (key.contains("password") || key.contains("key") ? "***" : value))
        );
        System.out.println("==========================================\n");
    }
}