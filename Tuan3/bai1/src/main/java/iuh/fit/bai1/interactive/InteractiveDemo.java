package iuh.fit.bai1.interactive;

import iuh.fit.bai1.composite.*;
import iuh.fit.bai1.observer.*;
import iuh.fit.bai1.adapter.*;
import java.util.Scanner;

/**
 * Interactive demo cho phép user test từng pattern
 */
public class InteractiveDemo {
    private Scanner scanner;
    
    public InteractiveDemo() {
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("INTERACTIVE DESIGN PATTERNS DEMO");
        System.out.println("===================================");
        
        while (true) {
            showMainMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    compositePatternDemo();
                    break;
                case 2:
                    observerPatternDemo();
                    break;
                case 3:
                    adapterPatternDemo();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void showMainMenu() {
        System.out.println("\nMAIN MENU:");
        System.out.println("1. Composite Pattern (File System)");
        System.out.println("2. Observer Pattern (Notifications)");
        System.out.println("3. Adapter Pattern (Data Conversion)");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }
    
    private int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void compositePatternDemo() {
        System.out.println("\nCOMPOSITE PATTERN DEMO");
        System.out.println("========================");
        
        Directory root = new Directory("MyProject");
        
        while (true) {
            System.out.println("\nCurrent structure:");
            root.display(0);
            System.out.println("\nTotal size: " + root.getSize() + " bytes");
            
            System.out.println("\n1. Add File");
            System.out.println("2. Add Directory");
            System.out.println("3. Show Structure");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");
            
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    addFile(root);
                    break;
                case 2:
                    addDirectory(root);
                    break;
                case 3:
                    root.display(0);
                    break;
                case 0:
                    return;
            }
        }
    }
    
    private void addFile(Directory parent) {
        System.out.print("Enter file name: ");
        String name = scanner.nextLine();
        System.out.print("Enter file content: ");
        String content = scanner.nextLine();
        
        File file = new File(name, content);
        parent.add(file);
        System.out.println("✅ File added: " + name + " (" + file.getSize() + " bytes)");
    }
    
    private void addDirectory(Directory parent) {
        System.out.print("Enter directory name: ");
        String name = scanner.nextLine();
        
        Directory dir = new Directory(name);
        parent.add(dir);
        System.out.println("✅ Directory added: " + name);
    }
    
    private void observerPatternDemo() {
        System.out.println("\nOBSERVER PATTERN DEMO");
        System.out.println("========================");
        
        StockPrice stock = new StockPrice("DEMO", 100.0);
        Investor investor1 = new Investor("Alice", "Long-term");
        Investor investor2 = new Investor("Bob", "Day Trading");
        
        stock.addObserver(investor1);
        stock.addObserver(investor2);
        
        while (true) {
            System.out.println("\nCurrent stock price: $" + stock.getPrice());
            System.out.println("1. Change Stock Price");
            System.out.println("2. Add Observer");
            System.out.println("3. Remove Observer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");
            
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    changeStockPrice(stock);
                    break;
                case 2:
                    System.out.println("Observer added automatically for demo");
                    break;
                case 3:
                    System.out.println("Observer removed automatically for demo");
                    break;
                case 0:
                    return;
            }
        }
    }
    
    private void changeStockPrice(StockPrice stock) {
        System.out.print("Enter new price: $");
        try {
            double price = Double.parseDouble(scanner.nextLine());
            stock.setPrice(price);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid price format!");
        }
    }
    
    private void adapterPatternDemo() {
        System.out.println("\n🔌 ADAPTER PATTERN DEMO");
        System.out.println("=======================");
        
        XMLDataProcessor xmlProcessor = new XMLDataProcessor();
        XMLToJSONAdapter adapter = new XMLToJSONAdapter(xmlProcessor);
        
        while (true) {
            System.out.println("\n1. Process JSON Data");
            System.out.println("2. Process XML Data (via Adapter)");
            System.out.println("3. Test Custom Data");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");
            
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    testJSONData(adapter);
                    break;
                case 2:
                    testXMLData(adapter);
                    break;
                case 3:
                    testCustomData(adapter);
                    break;
                case 0:
                    return;
            }
        }
    }
    
    private void testJSONData(XMLToJSONAdapter adapter) {
        String jsonData = "{\"name\":\"Test User\",\"age\":\"25\",\"city\":\"Test City\"}";
        System.out.println("Processing JSON: " + jsonData);
        String result = adapter.processData(jsonData);
        System.out.println("Result: " + result);
    }
    
    private void testXMLData(XMLToJSONAdapter adapter) {
        String xmlData = "<person><name>XML User</name><age>30</age><city>XML City</city></person>";
        System.out.println("Processing XML: " + xmlData);
        String result = adapter.processData(xmlData);
        System.out.println("Result: " + result);
    }
    
    private void testCustomData(XMLToJSONAdapter adapter) {
        System.out.print("Enter your data: ");
        String data = scanner.nextLine();
        System.out.println("Processing: " + data);
        String result = adapter.processData(data);
        System.out.println("Result: " + result);
    }
}