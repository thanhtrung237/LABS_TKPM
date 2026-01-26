package iuh.fit.bai1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import iuh.fit.bai1.demo.*;
import iuh.fit.bai1.interactive.InteractiveDemo;

@SpringBootApplication
public class Bai1Application {

    public static void main(String[] args) {
        // Mặc định chạy Spring Boot để có thể test qua web browser
        String mode = System.getProperty("demo.mode", "spring");
        
        if ("interactive".equals(mode)) {
            // Interactive mode - cho phép user test thủ công
            InteractiveDemo demo = new InteractiveDemo();
            demo.start();
        } else if ("auto".equals(mode)) {
            // Auto demo mode - chạy tất cả demo tự động
            System.out.println("\nDESIGN PATTERNS DEMONSTRATION\n");
            CompositePatternDemo.runDemo();
            System.out.println("\n" + "=".repeat(80) + "\n");
            ObserverPatternDemo.runDemo();
            System.out.println("\n" + "=".repeat(80) + "\n");
            AdapterPatternDemo.runDemo();
            System.out.println("Hoàn thành demo các Design Patterns!");
        } else {
            // Spring Boot mode - chạy web server (default)
            System.out.println("Starting Spring Boot Web Server...");
            System.out.println("Open browser: http://localhost:8080");
            System.out.println("Test Design Patterns via Web UI!");
            SpringApplication.run(Bai1Application.class, args);
        }
    }

}
