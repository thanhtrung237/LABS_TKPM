package iuh.fit.bai1.demo;

import iuh.fit.bai1.composite.*;

/**
 * Demo class cho Composite Pattern - File System Management
 */
public class CompositePatternDemo {
    
    public static void runDemo() {
        System.out.println("=== COMPOSITE PATTERN DEMO - FILE SYSTEM ===\n");
        
        // Tạo root directory
        Directory root = new Directory("root");
        
        // Tạo các file trong root
        File readme = new File("README.md", "# Project Documentation\nThis is a sample project.");
        File config = new File("config.json", "{\"version\": \"1.0\", \"debug\": true}");
        
        // Tạo thư mục src
        Directory src = new Directory("src");
        File mainJava = new File("Main.java", "public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello World!\");\n    }\n}");
        File utilsJava = new File("Utils.java", "public class Utils {\n    // Utility methods\n}");
        
        // Tạo thư mục test
        Directory test = new Directory("test");
        File testJava = new File("MainTest.java", "public class MainTest {\n    // Test cases\n}");
        
        // Tạo thư mục docs với subdirectory
        Directory docs = new Directory("docs");
        Directory api = new Directory("api");
        File apiDoc = new File("api.md", "# API Documentation\nAPI endpoints and usage");
        
        // Xây dựng cấu trúc cây
        root.add(readme);
        root.add(config);
        root.add(src);
        root.add(test);
        root.add(docs);
        
        src.add(mainJava);
        src.add(utilsJava);
        
        test.add(testJava);
        
        docs.add(api);
        api.add(apiDoc);
        
        // Hiển thị cấu trúc file system
        System.out.println("Cấu trúc File System:");
        root.display(0);
        
        System.out.println("\nThông tin chi tiết:");
        System.out.println("- Tổng dung lượng root: " + root.getSize() + " bytes");
        System.out.println("- Số items trong src: " + src.getChildCount());
        System.out.println("- Dung lượng thư mục src: " + src.getSize() + " bytes");
        
        // Demo thêm file mới
        System.out.println("\nThêm file mới vào src...");
        File newFile = new File("Database.java", "public class Database {\n    // Database connection logic\n}");
        src.add(newFile);
        
        System.out.println("Cấu trúc sau khi thêm file:");
        src.display(0);
        System.out.println("- Dung lượng mới của src: " + src.getSize() + " bytes");
        
        // Demo xóa file
        System.out.println("\n🗑Xóa file Utils.java...");
        src.remove(utilsJava);
        System.out.println("Cấu trúc sau khi xóa:");
        src.display(0);
    }
}