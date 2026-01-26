package iuh.fit.bai1.controller;

import iuh.fit.bai1.composite.*;
import iuh.fit.bai1.observer.*;
import iuh.fit.bai1.adapter.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * REST API Controller để test các Design Patterns với Email Notifications
 */
@RestController
@RequestMapping("/api/patterns")
public class PatternController {
    
    // In-memory storage cho email subscribers
    private static final Map<String, EmailSubscriber> emailSubscribers = new ConcurrentHashMap<>();
    
    // Email Subscriber Management Endpoints
    @PostMapping("/observer/add-subscriber")
    public ResponseEntity<Map<String, Object>> addEmailSubscriber(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String name = request.get("name");
        
        if (email == null || name == null || email.trim().isEmpty() || name.trim().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Email và tên không được để trống");
            return ResponseEntity.badRequest().body(response);
        }
        
        EmailSubscriber subscriber = new EmailSubscriber(email, name);
        emailSubscribers.put(email, subscriber);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Email subscriber đã được thêm thành công");
        response.put("subscriber", Map.of(
            "email", subscriber.getEmail(),
            "name", subscriber.getName()
        ));
        response.put("totalSubscribers", emailSubscribers.size());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/observer/subscribers")
    public ResponseEntity<Map<String, Object>> getEmailSubscribers() {
        List<Map<String, String>> subscriberList = new ArrayList<>();
        
        for (EmailSubscriber subscriber : emailSubscribers.values()) {
            subscriberList.add(Map.of(
                "email", subscriber.getEmail(),
                "name", subscriber.getName()
            ));
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("subscribers", subscriberList);
        response.put("totalCount", emailSubscribers.size());
        response.put("message", "Danh sách email subscribers");
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/observer/remove-subscriber/{email}")
    public ResponseEntity<Map<String, Object>> removeEmailSubscriber(@PathVariable String email) {
        EmailSubscriber removed = emailSubscribers.remove(email);
        
        Map<String, Object> response = new HashMap<>();
        if (removed != null) {
            response.put("success", true);
            response.put("message", "Email subscriber đã được xóa");
            response.put("removedSubscriber", Map.of(
                "email", removed.getEmail(),
                "name", removed.getName()
            ));
        } else {
            response.put("success", false);
            response.put("message", "Không tìm thấy email subscriber");
        }
        response.put("totalSubscribers", emailSubscribers.size());
        
        return ResponseEntity.ok(response);
    }
    
    // Composite Pattern Endpoints
    @PostMapping("/composite/filesystem")
    public ResponseEntity<Map<String, Object>> createFileSystem(@RequestBody Map<String, Object> request) {
        Directory root = new Directory("root");
        
        // Tạo sample file system
        File readme = new File("README.md", "# Sample Project");
        File config = new File("config.json", "{\"version\": \"1.0\"}");
        Directory src = new Directory("src");
        File mainJava = new File("Main.java", "public class Main {}");
        
        root.add(readme);
        root.add(config);
        root.add(src);
        src.add(mainJava);
        
        Map<String, Object> response = new HashMap<>();
        response.put("name", root.getName());
        response.put("totalSize", root.getSize());
        response.put("childCount", root.getChildCount());
        response.put("structure", getDirectoryStructure(root));
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/composite/add-file")
    public ResponseEntity<Map<String, Object>> addFile(@RequestBody Map<String, String> request) {
        String fileName = request.get("fileName");
        String content = request.get("content");
        
        File file = new File(fileName, content);
        
        Map<String, Object> response = new HashMap<>();
        response.put("fileName", file.getName());
        response.put("size", file.getSize());
        response.put("lastModified", file.getLastModified());
        response.put("message", "File created successfully");
        
        return ResponseEntity.ok(response);
    }
    
    // Observer Pattern Endpoints với Email Notifications
    @PostMapping("/observer/stock")
    public ResponseEntity<Map<String, Object>> createStock(@RequestBody Map<String, Object> request) {
        String symbol = (String) request.get("symbol");
        Double price = Double.valueOf(request.get("price").toString());
        
        StockPrice stock = new StockPrice(symbol, price);
        
        // Thêm email subscribers vào stock
        List<String> registrationLog = new ArrayList<>();
        List<String> subscriberNames = new ArrayList<>();
        
        for (EmailSubscriber subscriber : emailSubscribers.values()) {
            stock.addObserver(subscriber);
            registrationLog.add("✅ " + subscriber.toString() + " đã đăng ký nhận email thông báo cho " + symbol);
            subscriberNames.add(subscriber.toString());
        }
        
        if (emailSubscribers.isEmpty()) {
            registrationLog.add("⚠️ Chưa có email subscribers nào. Hãy thêm email để nhận thông báo!");
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("symbol", stock.getSymbol());
        response.put("price", stock.getPrice());
        response.put("observerCount", emailSubscribers.size());
        response.put("subscribers", subscriberNames);
        response.put("message", "Stock created successfully");
        response.put("registrationLog", registrationLog);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/observer/update-price")
    public ResponseEntity<Map<String, Object>> updateStockPrice(@RequestBody Map<String, Object> request) {
        String symbol = (String) request.get("symbol");
        Double newPrice = Double.valueOf(request.get("newPrice").toString());
        Double oldPrice = 100.0; // Default old price for demo
        
        StockPrice stock = new StockPrice(symbol, oldPrice);
        
        // Reset tất cả subscribers
        for (EmailSubscriber subscriber : emailSubscribers.values()) {
            subscriber.reset();
            stock.addObserver(subscriber);
        }
        
        // Cập nhật giá và capture email notifications
        stock.setPrice(newPrice);
        
        List<String> emailNotifications = new ArrayList<>();
        emailNotifications.add("🔔 Giá cổ phiếu " + symbol + " thay đổi: $" + oldPrice + " → $" + newPrice);
        emailNotifications.add("");
        emailNotifications.add("📧 EMAIL NOTIFICATIONS SENT:");
        emailNotifications.add("");
        
        boolean anyNotified = false;
        for (EmailSubscriber subscriber : emailSubscribers.values()) {
            if (subscriber.wasNotified()) {
                anyNotified = true;
                emailNotifications.add(subscriber.getLastNotification());
                emailNotifications.add("");
                emailNotifications.add("─".repeat(50));
                emailNotifications.add("");
            }
        }
        
        if (!anyNotified) {
            emailNotifications.add("⚠️ Không có email nào được gửi vì chưa có subscribers!");
            emailNotifications.add("💡 Hãy thêm email subscribers trước khi cập nhật giá.");
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("symbol", stock.getSymbol());
        response.put("oldPrice", oldPrice);
        response.put("newPrice", stock.getPrice());
        response.put("priceChange", newPrice - oldPrice);
        response.put("priceChangePercent", Math.round(((newPrice - oldPrice) / oldPrice) * 100 * 100.0) / 100.0);
        response.put("notified", anyNotified);
        response.put("emailsSent", emailSubscribers.size());
        response.put("emailNotifications", emailNotifications);
        response.put("message", anyNotified ? 
            "Price updated and email notifications sent to " + emailSubscribers.size() + " subscribers" :
            "Price updated but no email notifications sent (no subscribers)");
        
        // Pattern explanation
        response.put("patternExplanation", java.util.Arrays.asList(
            "🎯 Observer Pattern với Email Notifications:",
            "1. Subject (StockPrice) thay đổi state",
            "2. Subject.notifyObservers() được gọi",
            "3. Tất cả EmailSubscriber.update() được trigger",
            "4. Mỗi subscriber gửi email notification",
            "5. Real-world application: Email alerts, Push notifications"
        ));
        
        return ResponseEntity.ok(response);
    }
    
    // Adapter Pattern Endpoints
    @PostMapping("/adapter/process-json")
    public ResponseEntity<Map<String, Object>> processJSON(@RequestBody Map<String, String> request) {
        String jsonData = request.get("data");
        
        JSONDataProcessor processor = new JSONDataProcessor();
        String result = processor.processData(jsonData);
        
        Map<String, Object> response = new HashMap<>();
        response.put("inputData", jsonData);
        response.put("inputFormat", "JSON");
        response.put("result", result);
        response.put("success", result.startsWith("Processed JSON"));
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/adapter/process-xml")
    public ResponseEntity<Map<String, Object>> processXML(@RequestBody Map<String, String> request) {
        String xmlData = request.get("data");
        
        XMLDataProcessor xmlProcessor = new XMLDataProcessor();
        XMLToJSONAdapter adapter = new XMLToJSONAdapter(xmlProcessor);
        String result = adapter.processData(xmlData);
        
        Map<String, Object> response = new HashMap<>();
        response.put("inputData", xmlData);
        response.put("inputFormat", "XML");
        response.put("convertedToJSON", adapter.convertXMLToJSON(xmlData));
        response.put("result", result);
        response.put("success", result.startsWith("Processed JSON"));
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/observer/task")
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody Map<String, Object> request) {
        String taskName = (String) request.get("taskName");
        String status = (String) request.get("status");
        String assignee = (String) request.get("assignee");
        
        TaskStatus task = new TaskStatus(taskName, status, assignee);
        DetailedTeamMember developer = new DetailedTeamMember("Alice (Developer)", "Developer");
        DetailedTeamMember tester = new DetailedTeamMember("Bob (Tester)", "Tester");
        DetailedTeamMember pm = new DetailedTeamMember("Charlie (PM)", "Project Manager");
        
        task.addObserver(developer);
        task.addObserver(tester);
        task.addObserver(pm);
        
        Map<String, Object> response = new HashMap<>();
        response.put("taskName", task.getTaskName());
        response.put("status", task.getStatus());
        response.put("assignee", task.getAssignee());
        response.put("observerCount", 3);
        response.put("observers", java.util.Arrays.asList(
            developer.getName(), tester.getName(), pm.getName()
        ));
        response.put("message", "Task created successfully");
        response.put("registrationLog", java.util.Arrays.asList(
            "✅ " + developer.getName() + " đã đăng ký theo dõi task: " + taskName,
            "✅ " + tester.getName() + " đã đăng ký theo dõi task: " + taskName,
            "✅ " + pm.getName() + " đã đăng ký theo dõi task: " + taskName
        ));
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/observer/update-task")
    public ResponseEntity<Map<String, Object>> updateTaskStatus(@RequestBody Map<String, Object> request) {
        String taskName = (String) request.get("taskName");
        String newStatus = (String) request.get("newStatus");
        String oldStatus = "To Do"; // Default old status
        
        TaskStatus task = new TaskStatus(taskName, oldStatus, "Developer A");
        
        // Reset tất cả email subscribers
        for (EmailSubscriber subscriber : emailSubscribers.values()) {
            subscriber.reset();
            task.addObserver(subscriber);
        }
        
        // Update status và capture email notifications
        task.setStatus(newStatus);
        
        List<String> emailNotifications = new ArrayList<>();
        emailNotifications.add("📋 Task '" + taskName + "' chuyển từ '" + oldStatus + "' sang '" + newStatus + "'");
        emailNotifications.add("");
        emailNotifications.add("📧 EMAIL NOTIFICATIONS SENT:");
        emailNotifications.add("");
        
        boolean anyNotified = false;
        for (EmailSubscriber subscriber : emailSubscribers.values()) {
            if (subscriber.wasNotified()) {
                anyNotified = true;
                emailNotifications.add(subscriber.getLastNotification());
                emailNotifications.add("");
                emailNotifications.add("─".repeat(50));
                emailNotifications.add("");
            }
        }
        
        if (!anyNotified) {
            emailNotifications.add("⚠️ Không có email nào được gửi vì chưa có subscribers!");
            emailNotifications.add("💡 Hãy thêm email subscribers trước khi cập nhật task.");
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("taskName", task.getTaskName());
        response.put("oldStatus", oldStatus);
        response.put("newStatus", task.getStatus());
        response.put("assignee", task.getAssignee());
        response.put("notified", anyNotified);
        response.put("emailsSent", emailSubscribers.size());
        response.put("emailNotifications", emailNotifications);
        response.put("message", anyNotified ? 
            "Task status updated and email notifications sent to " + emailSubscribers.size() + " subscribers" :
            "Task status updated but no email notifications sent (no subscribers)");
        
        return ResponseEntity.ok(response);
    }
    
    private String getResponseByStatus(String status, String role) {
        switch (status.toLowerCase()) {
            case "completed":
                if ("Developer".equals(role)) return "Tuyệt vời! Code đã hoàn thành.";
                if ("Tester".equals(role)) return "Sẵn sàng để test!";
                if ("PM".equals(role)) return "Great job team! Task completed.";
                break;
            case "in progress":
                if ("Developer".equals(role)) return "Đang code, sẽ update tiến độ.";
                if ("Tester".equals(role)) return "Đang chuẩn bị test cases.";
                if ("PM".equals(role)) return "Theo dõi tiến độ...";
                break;
            case "blocked":
                if ("Developer".equals(role)) return "Cần hỗ trợ để giải quyết!";
                if ("Tester".equals(role)) return "Sẵn sàng hỗ trợ debug.";
                if ("PM".equals(role)) return "Cần can thiệp để unblock!";
                break;
            default:
                return "Đã ghi nhận thay đổi.";
        }
        return "Đã ghi nhận thay đổi.";
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Design Patterns API is running");
        return ResponseEntity.ok(response);
    }
    
    // Helper methods
    private Map<String, Object> getDirectoryStructure(Directory dir) {
        Map<String, Object> structure = new HashMap<>();
        structure.put("name", dir.getName());
        structure.put("type", "directory");
        structure.put("size", dir.getSize());
        structure.put("childCount", dir.getChildCount());
        return structure;
    }
    
    // Test Observer for API
    private static class DetailedInvestor implements Observer {
        private String name;
        private String strategy;
        private boolean notified = false;
        private int notificationCount = 0;
        
        public DetailedInvestor(String name, String strategy) {
            this.name = name;
            this.strategy = strategy;
        }
        
        @Override
        public void update(Subject subject) {
            this.notified = true;
            this.notificationCount++;
        }
        
        public boolean wasNotified() {
            return notified;
        }
        
        public String getName() {
            return name;
        }
        
        public String getStrategy() {
            return strategy;
        }
        
        public int getNotificationCount() {
            return notificationCount;
        }
    }
    
    private static class DetailedTeamMember implements Observer {
        private String name;
        private String role;
        private boolean notified = false;
        
        public DetailedTeamMember(String name, String role) {
            this.name = name;
            this.role = role;
        }
        
        @Override
        public void update(Subject subject) {
            this.notified = true;
        }
        
        public boolean wasNotified() {
            return notified;
        }
        
        public String getName() {
            return name;
        }
        
        public String getRole() {
            return role;
        }
    }
}