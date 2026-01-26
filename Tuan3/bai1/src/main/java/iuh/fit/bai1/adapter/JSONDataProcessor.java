package iuh.fit.bai1.adapter;

/**
 * Concrete implementation xử lý dữ liệu JSON
 */
public class JSONDataProcessor implements DataProcessor {
    
    @Override
    public String processData(String jsonData) {
        System.out.println("🔄 Xử lý dữ liệu JSON...");
        
        // Giả lập xử lý JSON
        if (jsonData.trim().startsWith("{") && jsonData.trim().endsWith("}")) {
            System.out.println("✅ JSON hợp lệ, đang xử lý...");
            return "Processed JSON: " + jsonData;
        } else {
            System.out.println("❌ Dữ liệu không phải JSON hợp lệ");
            return "Error: Invalid JSON format";
        }
    }
}