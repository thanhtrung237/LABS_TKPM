package iuh.fit.bai1.demo;

import iuh.fit.bai1.config.NotificationConfigManager;
import iuh.fit.bai1.factory.AbstractNotificationFactory;
import iuh.fit.bai1.factory.NotificationServiceFactory;
import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Interactive Demo cho Design Patterns
 * Chạy qua REST endpoint hoặc command line
 */
@Component
public class InteractiveDemo {
    
    private final Scanner scanner = new Scanner(System.in);
    private final NotificationServiceFactory factory = new NotificationServiceFactory();
    private final NotificationConfigManager config = NotificationConfigManager.getInstance();
    
    public void runInteractiveDemo() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎯 INTERACTIVE DESIGN PATTERN DEMO");
        System.out.println("=".repeat(60));
        
        while (true) {
            showMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    demonstrateSingleton();
                    break;
                case 2:
                    demonstrateFactoryMethod();
                    break;
                case 3:
                    demonstrateAbstractFactory();
                    break;
                case 4:
                    sendCustomNotification();
                    break;
                case 5:
                    showSystemStatus();
                    break;
                case 6:
                    configureSystem();
                    break;
                case 7:
                    runPerformanceTest();
                    break;
                case 0:
                    System.out.println("👋 Goodbye! Thanks for testing Design Patterns!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void showMenu() {
        System.out.println("\n📋 CHOOSE A DEMO:");
        System.out.println("1. 🔹 Singleton Pattern Demo");
        System.out.println("2. 🏭 Factory Method Pattern Demo");
        System.out.println("3. 🏗️ Abstract Factory Pattern Demo");
        System.out.println("4. 📧 Send Custom Notification");
        System.out.println("5. 📊 Show System Status");
        System.out.println("6. ⚙️ Configure System");
        System.out.println("7. ⚡ Performance Test");
        System.out.println("0. 🚪 Exit");
        System.out.print("\nEnter your choice (0-7): ");
    }
    
    private int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void demonstrateSingleton() {
        System.out.println("\n🔹 SINGLETON PATTERN DEMONSTRATION");
        System.out.println("-".repeat(50));
        
        System.out.println("Creating first instance...");
        NotificationConfigManager config1 = NotificationConfigManager.getInstance();
        System.out.println("Instance 1 hash: " + config1.hashCode());
        
        System.out.println("\nCreating second instance...");
        NotificationConfigManager config2 = NotificationConfigManager.getInstance();
        System.out.println("Instance 2 hash: " + config2.hashCode());
        
        System.out.println("\nAre they the same instance? " + (config1 == config2));
        
        System.out.println("\nTesting shared state...");
        config1.setConfiguration("demo.test", "singleton_demo_value");
        String value = config2.getConfiguration("demo.test");
        System.out.println("Value from instance 2: " + value);
        
        System.out.println("\n✅ Singleton Pattern: Only one instance exists and state is shared!");
    }
    
    private void demonstrateFactoryMethod() {
        System.out.println("\n🏭 FACTORY METHOD PATTERN DEMONSTRATION");
        System.out.println("-".repeat(50));
        
        System.out.println("Available notification types: " + String.join(", ", factory.getSupportedTypes()));
        
        System.out.print("Enter notification type (EMAIL/SMS/PUSH): ");
        String type = scanner.nextLine().toUpperCase();
        
        try {
            System.out.println("\nCreating " + type + " service using Factory...");
            NotificationService service = factory.createNotificationService(type);
            
            System.out.println("✅ Service created successfully!");
            System.out.println("Service type: " + service.getServiceType());
            System.out.println("Service class: " + service.getClass().getSimpleName());
            System.out.println("Service available: " + service.isAvailable());
            
            // Demo sending notification
            NotificationMessage message = new NotificationMessage(
                "demo@example.com",
                "Factory Demo",
                "This notification was created using Factory Method Pattern",
                "MEDIUM"
            );
            
            System.out.println("\nSending demo notification...");
            boolean result = service.sendNotification(message);
            System.out.println("Result: " + (result ? "✅ Success" : "❌ Failed"));
            
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private void demonstrateAbstractFactory() {
        System.out.println("\n🏗️ ABSTRACT FACTORY PATTERN DEMONSTRATION");
        System.out.println("-".repeat(50));
        
        System.out.println("Available environments: PRODUCTION, DEVELOPMENT, TEST");
        System.out.print("Enter environment: ");
        String environment = scanner.nextLine().toUpperCase();
        
        System.out.print("Enter notification type (EMAIL/SMS/PUSH): ");
        String type = scanner.nextLine().toUpperCase();
        
        try {
            System.out.println("\nCreating " + environment + " factory...");
            AbstractNotificationFactory abstractFactory = AbstractNotificationFactory.getFactory(environment);
            
            System.out.println("Creating " + type + " service...");
            NotificationService service = abstractFactory.createNotificationService(type);
            
            System.out.println("✅ Service created successfully!");
            System.out.println("Service type: " + service.getServiceType());
            System.out.println("Service class: " + service.getClass().getSimpleName());
            
            // Demo batch service
            System.out.println("\nCreating batch service...");
            NotificationService batchService = abstractFactory.createBatchNotificationService(type);
            System.out.println("Batch service type: " + batchService.getServiceType());
            
            // Send demo notifications
            NotificationMessage message = new NotificationMessage(
                "abstract@example.com",
                "Abstract Factory Demo",
                "This notification was created using Abstract Factory Pattern for " + environment,
                "HIGH"
            );
            
            System.out.println("\nSending regular notification...");
            service.sendNotification(message);
            
            System.out.println("\nSending batch notification...");
            batchService.sendNotification(message);
            
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private void sendCustomNotification() {
        System.out.println("\n📧 SEND CUSTOM NOTIFICATION");
        System.out.println("-".repeat(50));
        
        System.out.print("Enter recipient: ");
        String recipient = scanner.nextLine();
        
        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();
        
        System.out.print("Enter content: ");
        String content = scanner.nextLine();
        
        System.out.print("Enter priority (HIGH/MEDIUM/LOW): ");
        String priority = scanner.nextLine().toUpperCase();
        
        System.out.print("Enter notification type (EMAIL/SMS/PUSH): ");
        String type = scanner.nextLine().toUpperCase();
        
        try {
            NotificationMessage message = new NotificationMessage(recipient, subject, content, priority);
            NotificationService service = factory.createNotificationService(type);
            
            System.out.println("\nSending notification...");
            boolean result = service.sendNotification(message);
            
            if (result) {
                System.out.println("✅ Notification sent successfully!");
            } else {
                System.out.println("❌ Failed to send notification!");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private void showSystemStatus() {
        System.out.println("\n📊 SYSTEM STATUS");
        System.out.println("-".repeat(50));
        
        System.out.println("Supported notification types: " + String.join(", ", factory.getSupportedTypes()));
        
        System.out.println("\nService Status:");
        config.getServiceStatuses().forEach((service, status) -> 
            System.out.println("  " + service + ": " + (status ? "✅ Enabled" : "❌ Disabled"))
        );
        
        System.out.println("\nSystem Configurations:");
        config.getAllConfigurations().forEach((key, value) -> {
            String displayValue = (key.contains("password") || key.contains("key")) ? "***" : value;
            System.out.println("  " + key + ": " + displayValue);
        });
    }
    
    private void configureSystem() {
        System.out.println("\n⚙️ CONFIGURE SYSTEM");
        System.out.println("-".repeat(50));
        
        System.out.println("1. Enable/Disable Service");
        System.out.println("2. Set Configuration");
        System.out.print("Choose option (1-2): ");
        
        int option = getChoice();
        
        switch (option) {
            case 1:
                System.out.print("Enter service name (EMAIL/SMS/PUSH): ");
                String service = scanner.nextLine().toUpperCase();
                System.out.print("Enable service? (y/n): ");
                String enable = scanner.nextLine().toLowerCase();
                
                if ("y".equals(enable)) {
                    config.enableService(service);
                    System.out.println("✅ Service " + service + " enabled");
                } else {
                    config.disableService(service);
                    System.out.println("❌ Service " + service + " disabled");
                }
                break;
                
            case 2:
                System.out.print("Enter configuration key: ");
                String key = scanner.nextLine();
                System.out.print("Enter configuration value: ");
                String value = scanner.nextLine();
                
                config.setConfiguration(key, value);
                System.out.println("✅ Configuration set: " + key + " = " + value);
                break;
                
            default:
                System.out.println("❌ Invalid option");
        }
    }
    
    private void runPerformanceTest() {
        System.out.println("\n⚡ PERFORMANCE TEST");
        System.out.println("-".repeat(50));
        
        System.out.println("Testing Singleton getInstance() performance...");
        
        long startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            NotificationConfigManager.getInstance();
        }
        long endTime = System.nanoTime();
        
        long duration = endTime - startTime;
        double milliseconds = duration / 1_000_000.0;
        
        System.out.println("✅ 100,000 getInstance() calls completed in: " + milliseconds + " ms");
        System.out.println("Average per call: " + (duration / 100000.0) + " ns");
        
        // Memory test
        System.out.println("\nTesting memory efficiency...");
        NotificationConfigManager[] instances = new NotificationConfigManager[1000];
        for (int i = 0; i < 1000; i++) {
            instances[i] = NotificationConfigManager.getInstance();
        }
        
        boolean allSame = true;
        for (int i = 1; i < 1000; i++) {
            if (instances[0] != instances[i]) {
                allSame = false;
                break;
            }
        }
        
        System.out.println("✅ All 1000 instances are the same object: " + allSame);
        System.out.println("Memory efficiency: " + (allSame ? "EXCELLENT" : "POOR"));
    }
}