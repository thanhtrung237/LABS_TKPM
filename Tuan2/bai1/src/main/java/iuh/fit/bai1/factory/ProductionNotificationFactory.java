package iuh.fit.bai1.factory;

import iuh.fit.bai1.service.NotificationService;
import iuh.fit.bai1.service.impl.EmailNotificationService;
import iuh.fit.bai1.service.impl.PushNotificationService;
import iuh.fit.bai1.service.impl.SmsNotificationService;

/**
 * Concrete Factory cho Production environment
 * Tạo các service với cấu hình production
 */
public class ProductionNotificationFactory extends AbstractNotificationFactory {
    
    @Override
    public NotificationService createNotificationService(String type) {
        System.out.println("🏭 Production Factory creating " + type + " service");
        
        switch (type.toUpperCase()) {
            case "EMAIL":
                return new EmailNotificationService();
            case "SMS":
                return new SmsNotificationService();
            case "PUSH":
                return new PushNotificationService();
            default:
                throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
    }
    
    @Override
    public NotificationService createBatchNotificationService(String type) {
        System.out.println("🏭 Production Factory creating BATCH " + type + " service");
        
        // Trong production, batch service có thể có logic khác
        // Ví dụ: rate limiting, queue management, etc.
        NotificationService service = createNotificationService(type);
        
        // Wrap với batch processing logic (simplified)
        return new BatchNotificationWrapper(service);
    }
    
    /**
     * Wrapper class cho batch processing
     */
    private static class BatchNotificationWrapper implements NotificationService {
        private final NotificationService wrappedService;
        
        public BatchNotificationWrapper(NotificationService service) {
            this.wrappedService = service;
        }
        
        @Override
        public boolean sendNotification(iuh.fit.bai1.model.NotificationMessage message) {
            System.out.println("📦 Batch processing enabled for production");
            return wrappedService.sendNotification(message);
        }
        
        @Override
        public String getServiceType() {
            return "BATCH_" + wrappedService.getServiceType();
        }
        
        @Override
        public boolean isAvailable() {
            return wrappedService.isAvailable();
        }
    }
}