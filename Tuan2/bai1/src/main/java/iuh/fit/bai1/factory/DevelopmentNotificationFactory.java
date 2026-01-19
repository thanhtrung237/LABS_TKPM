package iuh.fit.bai1.factory;

import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;

/**
 * Concrete Factory cho Development environment
 * Tạo các mock service cho development
 */
public class DevelopmentNotificationFactory extends AbstractNotificationFactory {
    
    @Override
    public NotificationService createNotificationService(String type) {
        System.out.println("🏭 Development Factory creating MOCK " + type + " service");
        return new MockNotificationService(type);
    }
    
    @Override
    public NotificationService createBatchNotificationService(String type) {
        System.out.println("🏭 Development Factory creating MOCK BATCH " + type + " service");
        return new MockNotificationService("BATCH_" + type);
    }
    
    /**
     * Mock service cho development environment
     */
    private static class MockNotificationService implements NotificationService {
        private final String serviceType;
        
        public MockNotificationService(String serviceType) {
            this.serviceType = serviceType;
        }
        
        @Override
        public boolean sendNotification(NotificationMessage message) {
            System.out.println("🧪 MOCK " + serviceType + " - Development Mode");
            System.out.println("   To: " + message.getRecipient());
            System.out.println("   Subject: " + message.getSubject());
            System.out.println("   Content: " + message.getContent());
            System.out.println("   ✅ Mock notification sent successfully!");
            return true;
        }
        
        @Override
        public String getServiceType() {
            return serviceType;
        }
        
        @Override
        public boolean isAvailable() {
            return true; // Mock services are always available
        }
    }
}