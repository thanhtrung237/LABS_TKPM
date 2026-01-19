package iuh.fit.bai1.factory;

import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;

/**
 * Concrete Factory cho Test environment
 * Tạo các test service với logging chi tiết
 */
public class TestNotificationFactory extends AbstractNotificationFactory {
    
    @Override
    public NotificationService createNotificationService(String type) {
        System.out.println("🏭 Test Factory creating TEST " + type + " service");
        return new TestNotificationService(type);
    }
    
    @Override
    public NotificationService createBatchNotificationService(String type) {
        System.out.println("🏭 Test Factory creating TEST BATCH " + type + " service");
        return new TestNotificationService("BATCH_" + type);
    }
    
    /**
     * Test service với logging chi tiết cho testing
     */
    private static class TestNotificationService implements NotificationService {
        private final String serviceType;
        private static int callCount = 0;
        
        public TestNotificationService(String serviceType) {
            this.serviceType = serviceType;
        }
        
        @Override
        public boolean sendNotification(NotificationMessage message) {
            callCount++;
            System.out.println("🧪 TEST " + serviceType + " - Call #" + callCount);
            System.out.println("   Recipient: " + message.getRecipient());
            System.out.println("   Subject: " + message.getSubject());
            System.out.println("   Content: " + message.getContent());
            System.out.println("   Priority: " + message.getPriority());
            System.out.println("   Timestamp: " + message.getTimestamp());
            System.out.println("   ✅ Test notification logged successfully!");
            
            // Simulate some test scenarios
            if (message.getRecipient().contains("fail")) {
                System.out.println("   ❌ Simulated failure for testing");
                return false;
            }
            
            return true;
        }
        
        @Override
        public String getServiceType() {
            return serviceType;
        }
        
        @Override
        public boolean isAvailable() {
            return true; // Test services are always available
        }
        
        public static int getCallCount() {
            return callCount;
        }
        
        public static void resetCallCount() {
            callCount = 0;
        }
    }
}