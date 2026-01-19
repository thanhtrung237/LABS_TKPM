package iuh.fit.bai1.config;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton Pattern - Quản lý cấu hình hệ thống thông báo
 * Đảm bảo chỉ có một instance duy nhất quản lý cấu hình
 */
@Component
public class NotificationConfigManager {
    
    private static NotificationConfigManager instance;
    private Map<String, String> configurations;
    private Map<String, Boolean> serviceStatus;
    
    private NotificationConfigManager() {
        initializeConfigurations();
    }
    
    /**
     * Singleton getInstance method
     * Thread-safe implementation
     */
    public static synchronized NotificationConfigManager getInstance() {
        if (instance == null) {
            instance = new NotificationConfigManager();
        }
        return instance;
    }
    
    private void initializeConfigurations() {
        configurations = new HashMap<>();
        serviceStatus = new HashMap<>();
        
        // Email configurations
        configurations.put("email.smtp.host", "smtp.gmail.com");
        configurations.put("email.smtp.port", "587");
        configurations.put("email.username", "system@company.com");
        configurations.put("email.password", "encrypted_password");
        
        // SMS configurations
        configurations.put("sms.api.url", "https://api.sms-provider.com");
        configurations.put("sms.api.key", "your_api_key");
        configurations.put("sms.sender.number", "+84123456789");
        
        // Push notification configurations
        configurations.put("push.firebase.key", "firebase_server_key");
        configurations.put("push.api.url", "https://fcm.googleapis.com/fcm/send");
        
        // Service status
        serviceStatus.put("EMAIL", true);
        serviceStatus.put("SMS", true);
        serviceStatus.put("PUSH", true);
        
        System.out.println("NotificationConfigManager initialized with configurations");
    }
    
    public String getConfiguration(String key) {
        return configurations.get(key);
    }
    
    public void setConfiguration(String key, String value) {
        configurations.put(key, value);
        System.out.println("Configuration updated: " + key + " = " + value);
    }
    
    public boolean isServiceEnabled(String serviceType) {
        return serviceStatus.getOrDefault(serviceType.toUpperCase(), false);
    }
    
    public void enableService(String serviceType) {
        serviceStatus.put(serviceType.toUpperCase(), true);
        System.out.println("Service enabled: " + serviceType);
    }
    
    public void disableService(String serviceType) {
        serviceStatus.put(serviceType.toUpperCase(), false);
        System.out.println("Service disabled: " + serviceType);
    }
    
    public Map<String, String> getAllConfigurations() {
        return new HashMap<>(configurations);
    }
    
    public Map<String, Boolean> getServiceStatuses() {
        return new HashMap<>(serviceStatus);
    }
}