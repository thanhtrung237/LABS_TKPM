package iuh.fit.bai1.factory;

import iuh.fit.bai1.service.NotificationService;
import iuh.fit.bai1.service.impl.EmailNotificationService;
import iuh.fit.bai1.service.impl.PushNotificationService;
import iuh.fit.bai1.service.impl.SmsNotificationService;
import org.springframework.stereotype.Component;

/**
 * Factory Pattern - Tạo các instance của NotificationService
 * Factory Method Pattern implementation
 */
@Component
public class NotificationServiceFactory {
    
    /**
     * Factory method để tạo NotificationService dựa trên type
     * @param type loại notification service (EMAIL, SMS, PUSH)
     * @return NotificationService instance tương ứng
     * @throws IllegalArgumentException nếu type không được hỗ trợ
     */
    public NotificationService createNotificationService(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification type cannot be null or empty");
        }
        
        switch (type.toUpperCase()) {
            case "EMAIL":
                System.out.println("🏭 Factory creating EmailNotificationService");
                return new EmailNotificationService();
                
            case "SMS":
                System.out.println("🏭 Factory creating SmsNotificationService");
                return new SmsNotificationService();
                
            case "PUSH":
                System.out.println("🏭 Factory creating PushNotificationService");
                return new PushNotificationService();
                
            default:
                throw new IllegalArgumentException("Unsupported notification type: " + type + 
                    ". Supported types: EMAIL, SMS, PUSH");
        }
    }
    
    /**
     * Method để lấy danh sách các loại notification được hỗ trợ
     * @return mảng các loại notification
     */
    public String[] getSupportedTypes() {
        return new String[]{"EMAIL", "SMS", "PUSH"};
    }
    
    /**
     * Kiểm tra xem một loại notification có được hỗ trợ không
     * @param type loại notification cần kiểm tra
     * @return true nếu được hỗ trợ, false nếu không
     */
    public boolean isTypeSupported(String type) {
        if (type == null) return false;
        
        for (String supportedType : getSupportedTypes()) {
            if (supportedType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}