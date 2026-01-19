package iuh.fit.bai1.demo;

import iuh.fit.bai1.config.NotificationConfigManager;
import iuh.fit.bai1.factory.AbstractNotificationFactory;
import iuh.fit.bai1.factory.NotificationServiceFactory;
import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Demo class để minh họa việc sử dụng Design Patterns
 */
@Component
public class DesignPatternDemo implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎯 DESIGN PATTERN DEMONSTRATION");
        System.out.println("=".repeat(60));
        
        demonstrateSingletonPattern();
        demonstrateFactoryMethodPattern();
        demonstrateAbstractFactoryPattern();
        
        System.out.println("=".repeat(60));
        System.out.println("✅ DEMO COMPLETED SUCCESSFULLY!");
        System.out.println("=".repeat(60) + "\n");
    }
    
    /**
     * Demo Singleton Pattern
     */
    private void demonstrateSingletonPattern() {
        System.out.println("\n🔹 SINGLETON PATTERN DEMONSTRATION");
        System.out.println("-".repeat(40));
        
        // Lấy instance đầu tiên
        NotificationConfigManager config1 = NotificationConfigManager.getInstance();
        System.out.println("📍 First instance created: " + config1.hashCode());
        
        // Lấy instance thứ hai
        NotificationConfigManager config2 = NotificationConfigManager.getInstance();
        System.out.println("📍 Second instance created: " + config2.hashCode());
        
        // Kiểm tra xem có phải cùng một instance không
        System.out.println("🔍 Are they the same instance? " + (config1 == config2));
        
        // Thay đổi cấu hình từ instance đầu tiên
        config1.setConfiguration("test.key", "test.value");
        
        // Kiểm tra từ instance thứ hai
        String value = config2.getConfiguration("test.key");
        System.out.println("🔍 Configuration shared between instances: " + value);
        
        // Thử tắt/bật service
        config1.disableService("EMAIL");
        System.out.println("🔍 Email service status from config2: " + config2.isServiceEnabled("EMAIL"));
        
        config1.enableService("EMAIL");
        System.out.println("🔍 Email service status after re-enable: " + config2.isServiceEnabled("EMAIL"));
        
        System.out.println("✅ Singleton Pattern: Chỉ có một instance duy nhất được tạo và chia sẻ cấu hình");
    }
    
    /**
     * Demo Factory Method Pattern
     */
    private void demonstrateFactoryMethodPattern() {
        System.out.println("\n🔹 FACTORY METHOD PATTERN DEMONSTRATION");
        System.out.println("-".repeat(40));
        
        NotificationServiceFactory factory = new NotificationServiceFactory();
        
        // Tạo notification message mẫu
        NotificationMessage message = new NotificationMessage(
            "user@example.com",
            "Test Notification",
            "This is a test message from Factory Method Pattern demo",
            "HIGH"
        );
        
        // Demo tạo các loại service khác nhau
        String[] types = {"EMAIL", "SMS", "PUSH"};
        
        for (String type : types) {
            try {
                System.out.println("\n🏭 Creating " + type + " service using Factory Method...");
                
                // Factory tạo service dựa trên type
                NotificationService service = factory.createNotificationService(type);
                
                System.out.println("   Service type: " + service.getServiceType());
                System.out.println("   Service available: " + service.isAvailable());
                
                // Gửi notification
                service.sendNotification(message);
                
            } catch (Exception e) {
                System.err.println("❌ Error creating " + type + " service: " + e.getMessage());
            }
        }
        
        // Demo error handling
        try {
            System.out.println("\n🏭 Trying to create unsupported service type...");
            factory.createNotificationService("INVALID_TYPE");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Factory correctly handled invalid type: " + e.getMessage());
        }
        
        System.out.println("✅ Factory Method Pattern: Tạo objects mà không cần biết class cụ thể");
    }
    
    /**
     * Demo Abstract Factory Pattern
     */
    private void demonstrateAbstractFactoryPattern() {
        System.out.println("\n🔹 ABSTRACT FACTORY PATTERN DEMONSTRATION");
        System.out.println("-".repeat(40));
        
        NotificationMessage message = new NotificationMessage(
            "test@example.com",
            "Abstract Factory Test",
            "Testing Abstract Factory Pattern with different environments",
            "MEDIUM"
        );
        
        String[] environments = {"PRODUCTION", "DEVELOPMENT", "TEST"};
        String[] serviceTypes = {"EMAIL", "SMS"};
        
        for (String environment : environments) {
            System.out.println("\n🌍 Testing " + environment + " Environment Factory:");
            
            try {
                // Lấy factory cho environment cụ thể
                AbstractNotificationFactory factory = AbstractNotificationFactory.getFactory(environment);
                
                for (String serviceType : serviceTypes) {
                    System.out.println("\n  📧 Creating " + serviceType + " service:");
                    
                    // Tạo regular service
                    NotificationService regularService = factory.createNotificationService(serviceType);
                    regularService.sendNotification(message);
                    
                    // Tạo batch service
                    System.out.println("\n  📦 Creating BATCH " + serviceType + " service:");
                    NotificationService batchService = factory.createBatchNotificationService(serviceType);
                    batchService.sendNotification(message);
                }
                
            } catch (Exception e) {
                System.err.println("❌ Error with " + environment + " factory: " + e.getMessage());
            }
        }
        
        // Demo error handling cho unsupported environment
        try {
            System.out.println("\n🌍 Trying unsupported environment...");
            AbstractNotificationFactory.getFactory("INVALID_ENV");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Abstract Factory correctly handled invalid environment: " + e.getMessage());
        }
        
        System.out.println("✅ Abstract Factory Pattern: Tạo family of objects cho từng environment");
    }
}