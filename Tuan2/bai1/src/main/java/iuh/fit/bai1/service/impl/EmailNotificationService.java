package iuh.fit.bai1.service.impl;

import iuh.fit.bai1.config.NotificationConfigManager;
import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * Concrete implementation cho Email notification
 */
@Service
public class EmailNotificationService implements NotificationService {
    
    private final NotificationConfigManager configManager;
    
    public EmailNotificationService() {
        this.configManager = NotificationConfigManager.getInstance();
    }
    
    @Override
    public boolean sendNotification(NotificationMessage message) {
        if (!isAvailable()) {
            System.out.println("Email service is not available");
            return false;
        }
        
        try {
            // Simulate email sending logic
            String smtpHost = configManager.getConfiguration("email.smtp.host");
            String smtpPort = configManager.getConfiguration("email.smtp.port");
            
            System.out.println("=== SENDING EMAIL ===");
            System.out.println("SMTP Host: " + smtpHost + ":" + smtpPort);
            System.out.println("To: " + message.getRecipient());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Content: " + message.getContent());
            System.out.println("Priority: " + message.getPriority());
            System.out.println("Timestamp: " + message.getTimestamp());
            
            // Simulate processing time
            Thread.sleep(1000);
            
            System.out.println("✅ Email sent successfully!");
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String getServiceType() {
        return "EMAIL";
    }
    
    @Override
    public boolean isAvailable() {
        return configManager.isServiceEnabled("EMAIL");
    }
}