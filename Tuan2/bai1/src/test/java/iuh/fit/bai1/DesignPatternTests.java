package iuh.fit.bai1;

import iuh.fit.bai1.config.NotificationConfigManager;
import iuh.fit.bai1.factory.AbstractNotificationFactory;
import iuh.fit.bai1.factory.NotificationServiceFactory;
import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;
import iuh.fit.bai1.service.impl.EmailNotificationService;
import iuh.fit.bai1.service.impl.SmsNotificationService;
import iuh.fit.bai1.service.impl.PushNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests để minh chứng các Design Patterns
 */
@SpringBootTest
public class DesignPatternTests {

    @Test
    @DisplayName("Singleton Pattern - Chỉ có một instance duy nhất")
    public void testSingletonPattern() {
        System.out.println("\n🧪 TESTING SINGLETON PATTERN");
        System.out.println("=" .repeat(50));
        
        // Lấy hai instance
        NotificationConfigManager config1 = NotificationConfigManager.getInstance();
        NotificationConfigManager config2 = NotificationConfigManager.getInstance();
        
        // Kiểm tra cùng một instance
        assertSame(config1, config2, "Should be the same instance");
        System.out.println("✅ Same instance check: PASSED");
        
        // Kiểm tra hash code
        assertEquals(config1.hashCode(), config2.hashCode(), "Hash codes should be equal");
        System.out.println("✅ Hash code check: PASSED");
        
        // Kiểm tra chia sẻ state
        config1.setConfiguration("test.singleton", "singleton_value");
        String value = config2.getConfiguration("test.singleton");
        assertEquals("singleton_value", value, "Should share the same state");
        System.out.println("✅ Shared state check: PASSED");
        
        // Kiểm tra service status sharing
        config1.disableService("EMAIL");
        assertFalse(config2.isServiceEnabled("EMAIL"), "Service status should be shared");
        config1.enableService("EMAIL"); // Reset
        System.out.println("✅ Service status sharing: PASSED");
        
        System.out.println("🎉 SINGLETON PATTERN TEST: ALL PASSED\n");
    }

    @Test
    @DisplayName("Factory Method Pattern - Tạo đúng loại service")
    public void testFactoryMethodPattern() {
        System.out.println("\n🧪 TESTING FACTORY METHOD PATTERN");
        System.out.println("=" .repeat(50));
        
        NotificationServiceFactory factory = new NotificationServiceFactory();
        
        // Test tạo Email service
        NotificationService emailService = factory.createNotificationService("EMAIL");
        assertTrue(emailService instanceof EmailNotificationService, "Should create EmailNotificationService");
        assertEquals("EMAIL", emailService.getServiceType(), "Service type should be EMAIL");
        System.out.println("✅ Email service creation: PASSED");
        
        // Test tạo SMS service
        NotificationService smsService = factory.createNotificationService("SMS");
        assertTrue(smsService instanceof SmsNotificationService, "Should create SmsNotificationService");
        assertEquals("SMS", smsService.getServiceType(), "Service type should be SMS");
        System.out.println("✅ SMS service creation: PASSED");
        
        // Test tạo Push service
        NotificationService pushService = factory.createNotificationService("PUSH");
        assertTrue(pushService instanceof PushNotificationService, "Should create PushNotificationService");
        assertEquals("PUSH", pushService.getServiceType(), "Service type should be PUSH");
        System.out.println("✅ Push service creation: PASSED");
        
        // Test case insensitive
        NotificationService emailService2 = factory.createNotificationService("email");
        assertTrue(emailService2 instanceof EmailNotificationService, "Should handle case insensitive");
        System.out.println("✅ Case insensitive: PASSED");
        
        // Test error handling
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createNotificationService("INVALID_TYPE");
        }, "Should throw exception for invalid type");
        System.out.println("✅ Error handling: PASSED");
        
        // Test supported types
        String[] supportedTypes = factory.getSupportedTypes();
        assertEquals(3, supportedTypes.length, "Should have 3 supported types");
        assertTrue(factory.isTypeSupported("EMAIL"), "EMAIL should be supported");
        assertFalse(factory.isTypeSupported("INVALID"), "INVALID should not be supported");
        System.out.println("✅ Supported types check: PASSED");
        
        System.out.println("🎉 FACTORY METHOD PATTERN TEST: ALL PASSED\n");
    }

    @Test
    @DisplayName("Abstract Factory Pattern - Tạo services cho các environment")
    public void testAbstractFactoryPattern() {
        System.out.println("\n🧪 TESTING ABSTRACT FACTORY PATTERN");
        System.out.println("=" .repeat(50));
        
        // Test Production Factory
        AbstractNotificationFactory prodFactory = AbstractNotificationFactory.getFactory("PRODUCTION");
        assertNotNull(prodFactory, "Production factory should not be null");
        
        NotificationService prodEmailService = prodFactory.createNotificationService("EMAIL");
        assertNotNull(prodEmailService, "Production email service should not be null");
        System.out.println("✅ Production factory: PASSED");
        
        // Test Development Factory
        AbstractNotificationFactory devFactory = AbstractNotificationFactory.getFactory("DEVELOPMENT");
        assertNotNull(devFactory, "Development factory should not be null");
        
        NotificationService devEmailService = devFactory.createNotificationService("EMAIL");
        assertNotNull(devEmailService, "Development email service should not be null");
        System.out.println("✅ Development factory: PASSED");
        
        // Test Test Factory
        AbstractNotificationFactory testFactory = AbstractNotificationFactory.getFactory("TEST");
        assertNotNull(testFactory, "Test factory should not be null");
        
        NotificationService testEmailService = testFactory.createNotificationService("EMAIL");
        assertNotNull(testEmailService, "Test email service should not be null");
        System.out.println("✅ Test factory: PASSED");
        
        // Test Batch Services
        NotificationService prodBatchService = prodFactory.createBatchNotificationService("EMAIL");
        assertNotNull(prodBatchService, "Production batch service should not be null");
        assertTrue(prodBatchService.getServiceType().contains("BATCH"), "Should be batch service");
        System.out.println("✅ Batch service creation: PASSED");
        
        // Test case insensitive environment
        AbstractNotificationFactory prodFactory2 = AbstractNotificationFactory.getFactory("production");
        assertNotNull(prodFactory2, "Should handle case insensitive environment");
        System.out.println("✅ Case insensitive environment: PASSED");
        
        // Test error handling
        assertThrows(IllegalArgumentException.class, () -> {
            AbstractNotificationFactory.getFactory("INVALID_ENV");
        }, "Should throw exception for invalid environment");
        System.out.println("✅ Error handling: PASSED");
        
        System.out.println("🎉 ABSTRACT FACTORY PATTERN TEST: ALL PASSED\n");
    }

    @Test
    @DisplayName("Integration Test - Tất cả patterns hoạt động cùng nhau")
    public void testPatternsIntegration() {
        System.out.println("\n🧪 TESTING PATTERNS INTEGRATION");
        System.out.println("=" .repeat(50));
        
        // Sử dụng Singleton để cấu hình
        NotificationConfigManager config = NotificationConfigManager.getInstance();
        config.enableService("EMAIL");
        config.setConfiguration("test.integration", "integration_test");
        
        // Sử dụng Factory Method để tạo service
        NotificationServiceFactory factory = new NotificationServiceFactory();
        NotificationService emailService = factory.createNotificationService("EMAIL");
        
        // Sử dụng Abstract Factory cho environment
        AbstractNotificationFactory devFactory = AbstractNotificationFactory.getFactory("DEVELOPMENT");
        NotificationService devService = devFactory.createNotificationService("EMAIL");
        
        // Tạo notification message
        NotificationMessage message = new NotificationMessage(
            "integration@test.com",
            "Integration Test",
            "Testing all patterns together",
            "HIGH"
        );
        
        // Test gửi notification
        assertTrue(emailService.isAvailable(), "Email service should be available");
        boolean result1 = emailService.sendNotification(message);
        assertTrue(result1, "Should send notification successfully");
        
        boolean result2 = devService.sendNotification(message);
        assertTrue(result2, "Dev service should send notification successfully");
        
        // Kiểm tra configuration được chia sẻ
        String configValue = config.getConfiguration("test.integration");
        assertEquals("integration_test", configValue, "Configuration should be shared");
        
        System.out.println("✅ All patterns work together: PASSED");
        System.out.println("🎉 INTEGRATION TEST: ALL PASSED\n");
    }

    @Test
    @DisplayName("Performance Test - Singleton vs Multiple Instances")
    public void testSingletonPerformance() {
        System.out.println("\n🧪 TESTING SINGLETON PERFORMANCE");
        System.out.println("=" .repeat(50));
        
        long startTime, endTime;
        
        // Test Singleton performance
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            NotificationConfigManager.getInstance();
        }
        endTime = System.nanoTime();
        long singletonTime = endTime - startTime;
        
        System.out.println("⏱️ Singleton getInstance() 10,000 times: " + singletonTime + " ns");
        System.out.println("✅ Singleton performance test completed");
        
        // Verify memory efficiency
        NotificationConfigManager config1 = NotificationConfigManager.getInstance();
        NotificationConfigManager config2 = NotificationConfigManager.getInstance();
        NotificationConfigManager config3 = NotificationConfigManager.getInstance();
        
        // All should point to same memory location
        assertTrue(config1 == config2 && config2 == config3, "All instances should be the same object");
        System.out.println("✅ Memory efficiency: PASSED");
        
        System.out.println("🎉 PERFORMANCE TEST: COMPLETED\n");
    }
}