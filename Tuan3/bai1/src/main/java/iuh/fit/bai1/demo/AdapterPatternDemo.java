package iuh.fit.bai1.demo;

import iuh.fit.bai1.adapter.*;

/**
 * Demo class cho Adapter Pattern - JSON/XML Data Conversion
 */
public class AdapterPatternDemo {
    
    public static void runDemo() {
        System.out.println("=== ADAPTER PATTERN DEMO - JSON/XML CONVERSION ===\n");
        
        // Tạo các processor
        JSONDataProcessor jsonProcessor = new JSONDataProcessor();
        XMLDataProcessor xmlProcessor = new XMLDataProcessor();
        XMLToJSONAdapter adapter = new XMLToJSONAdapter(xmlProcessor);
        
        // Demo data
        String jsonData = "{\"name\":\"John Doe\",\"age\":\"30\",\"city\":\"New York\"}";
        String xmlData = "<person><name>Jane Smith</name><age>25</age><city>Los Angeles</city></person>";
        String invalidData = "This is not JSON or XML";
        
        System.out.println("Dữ liệu test:");
        System.out.println("JSON: " + jsonData);
        System.out.println("XML:  " + xmlData);
        System.out.println("Invalid: " + invalidData);
        System.out.println();
        
        // Demo 1: Xử lý JSON trực tiếp
        System.out.println("🔹 DEMO 1: Xử lý JSON với JSONDataProcessor");
        System.out.println("Result: " + jsonProcessor.processData(jsonData));
        System.out.println();
        
        // Demo 2: Xử lý XML trực tiếp (không thể với DataProcessor interface)
        System.out.println("🔹 DEMO 2: Xử lý XML với XMLDataProcessor (không qua interface)");
        System.out.println("Result: " + xmlProcessor.processXMLData(xmlData));
        System.out.println();
        
        // Demo 3: Sử dụng Adapter để xử lý XML
        System.out.println("🔹 DEMO 3: Xử lý XML thông qua Adapter");
        System.out.println("Result: " + adapter.processData(xmlData));
        System.out.println();
        
        // Demo 4: Adapter xử lý JSON (pass-through)
        System.out.println("🔹 DEMO 4: Adapter xử lý JSON (pass-through)");
        System.out.println("Result: " + adapter.processData(jsonData));
        System.out.println();
        
        // Demo 5: Adapter xử lý dữ liệu không hợp lệ
        System.out.println("🔹 DEMO 5: Adapter xử lý dữ liệu không hợp lệ");
        System.out.println("Result: " + adapter.processData(invalidData));
        System.out.println();
        
        // Demo 6: Client code sử dụng thống nhất
        System.out.println("🔹 DEMO 6: Client sử dụng thống nhất qua DataProcessor interface");
        clientCode(jsonProcessor, "JSON Processor", jsonData);
        clientCode(adapter, "XML-to-JSON Adapter", xmlData);
        clientCode(adapter, "XML-to-JSON Adapter", jsonData);
    }
    
    /**
     * Client code chỉ biết về DataProcessor interface
     * Có thể làm việc với cả JSON và XML thông qua adapter
     */
    private static void clientCode(DataProcessor processor, String processorName, String data) {
        System.out.println("Client sử dụng " + processorName + ":");
        String result = processor.processData(data);
        System.out.println("Kết quả: " + result);
        System.out.println();
    }
}