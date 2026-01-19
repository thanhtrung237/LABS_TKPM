package iuh.fit.bai1.service.impl;

import iuh.fit.bai1.config.NotificationConfigManager;
import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * Concrete implementation cho SMS notification
 */
@Service
public class SmsNotificationService implements NotificationService {
    
    private final NotificationConfigManager configManager;
    
    public SmsNotificationService() {
        this.configManager = NotificationConfigManager.getInstance();
    }
    
    @Override
    public boolean sendNotification(NotificationMessage message) {
        if (!isAvailable()) {
            System.out.println("SMS service is not available");
            return false;
        }
        
        try {
            // Simulate SMS sending logic
            String apiUrl = configManager.getConfiguration("sms.api.url");
            String senderNumber = configManager.getConfiguration("sms.sender.number");
            
            System.out.println("=== SENDING SMS ===");
            System.out.println("API URL: " + apiUrl);
            System.out.println("From: " + senderNumber);
            System.out.println("To: " + message.getRecipient());
            System.out.println("Message: " + message.getContent());
            System.out.println("Priority: " + message.getPriority());
            System.out.println("Timestamp: " + message.getTimestamp());
            
            // Simulate processing time
            Thread.sleep(800);
            
            System.out.println("✅ SMS sent successfully!");
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Failed to send SMS: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String getServiceType() {
        return "SMS";
    }
    
    @Override
    public boolean isAvailable() {
        return configManager.isServiceEnabled("SMS");
    }
}