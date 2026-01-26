package iuh.fit.bai1.demo;

import iuh.fit.bai1.observer.*;

/**
 * Demo class cho Observer Pattern - Stock Price & Task Status
 */
public class ObserverPatternDemo {
    
    public static void runDemo() {
        System.out.println("=== OBSERVER PATTERN DEMO ===\n");
        
        // Demo 1: Stock Price Monitoring
        stockPriceDemo();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Demo 2: Task Status Monitoring
        taskStatusDemo();
    }
    
    private static void stockPriceDemo() {
        System.out.println("DEMO 1: THEO DÕI GIÁ CỔ PHIẾU\n");
        
        // Tạo cổ phiếu
        StockPrice appleStock = new StockPrice("AAPL", 150.0);
        StockPrice googleStock = new StockPrice("GOOGL", 2800.0);
        
        // Tạo các nhà đầu tư
        Investor investor1 = new Investor("Nguyễn Văn A", "Long-term Investment");
        Investor investor2 = new Investor("Trần Thị B", "Day Trading");
        Investor investor3 = new Investor("Lê Văn C", "Value Investing");
        
        // Đăng ký theo dõi
        appleStock.addObserver(investor1);
        appleStock.addObserver(investor2);
        
        googleStock.addObserver(investor2);
        googleStock.addObserver(investor3);
        
        System.out.println();
        
        // Thay đổi giá cổ phiếu
        System.out.println("Thay đổi giá AAPL từ $150 -> $95:");
        appleStock.setPrice(95.0);
        
        System.out.println("\nThay đổi giá GOOGL từ $2800 -> $3100:");
        googleStock.setPrice(3100.0);
        
        System.out.println("\nThay đổi giá AAPL từ $95 -> $120:");
        appleStock.setPrice(120.0);
        
        // Hủy đăng ký
        System.out.println("\nInvestor B hủy theo dõi AAPL:");
        appleStock.removeObserver(investor2);
        
        System.out.println("\nThay đổi giá AAPL từ $120 -> $130 (chỉ Investor A nhận thông báo):");
        appleStock.setPrice(130.0);
    }
    
    private static void taskStatusDemo() {
        System.out.println("DEMO 2: THEO DÕI TRẠNG THÁI TASK\n");
        
        // Tạo tasks
        TaskStatus loginTask = new TaskStatus("Implement Login Feature", "To Do", "Developer A");
        TaskStatus testingTask = new TaskStatus("Write Unit Tests", "In Progress", "Tester B");
        
        // Tạo team members
        TeamMember developer = new TeamMember("Nguyễn Dev", "Developer");
        TeamMember tester = new TeamMember("Trần Test", "Tester");
        TeamMember projectManager = new TeamMember("Lê PM", "Project Manager");
        TeamMember designer = new TeamMember("Phạm Design", "UI/UX Designer");
        
        // Đăng ký theo dõi
        loginTask.addObserver(developer);
        loginTask.addObserver(projectManager);
        loginTask.addObserver(designer);
        
        testingTask.addObserver(tester);
        testingTask.addObserver(projectManager);
        
        System.out.println();
        
        // Thay đổi trạng thái tasks
        System.out.println("Chuyển Login task sang 'In Progress':");
        loginTask.setStatus("In Progress");
        
        System.out.println("\nChuyển Testing task sang 'Blocked':");
        testingTask.setStatus("Blocked");
        
        System.out.println("\nChuyển Login task sang 'Completed':");
        loginTask.setStatus("Completed");
        
        System.out.println("\nChuyển Testing task sang 'In Progress' (sau khi giải quyết vấn đề):");
        testingTask.setStatus("In Progress");
        
        System.out.println("\nChuyển Testing task sang 'Completed':");
        testingTask.setStatus("Completed");
    }
}