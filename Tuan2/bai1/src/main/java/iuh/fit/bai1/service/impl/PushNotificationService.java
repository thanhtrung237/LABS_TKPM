package iuh.fit.bai1.service.impl;

import iuh.fit.bai1.config.NotificationConfigManager;
import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * Concrete implementation cho Push notification
 */
@Service
public class PushNotificationService implements NotificationService {
    
    private final NotificationConfigManager configManager;
    
    public PushNotificationService() {
        this.configManager = NotificationConfigManager.getInstance();
    }
    
    @Override
    public boolean sendNotification(NotificationMessage message) {
        if (!isAvailable()) {
            System.out.println("Push notification service is not available");
            return false;
        }
        
        try {
            // Simulate push notification sending logic
            String apiUrl = configManager.getConfiguration("push.api.url");
            String firebaseKey = configManager.getConfiguration("push.firebase.key");
            
            System.out.println("=== SENDING PUSH NOTIFICATION ===");
            System.out.println("Firebase API URL: " + apiUrl);
            System.out.println("Device Token: " + message.getRecipient());
            System.out.println("Title: " + message.getSubject());
            System.out.println("Body: " + message.getContent());
            System.out.println("Priority: " + message.getPriority());
            System.out.println("Timestamp: " + message.getTimestamp());
            
            // Simulate processing time
            Thread.sleep(600);
            
            System.out.println("✅ Push notification sent successfully!");
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Failed to send push notification: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String getServiceType() {
        return "PUSH";
    }
    
    @Override
    public boolean isAvailable() {
        return configManager.isServiceEnabled("PUSH");
    }
}