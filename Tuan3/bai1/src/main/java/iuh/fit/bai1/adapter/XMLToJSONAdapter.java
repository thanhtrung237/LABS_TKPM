package iuh.fit.bai1.adapter;

/**
 * Adapter class chuyển đổi XML sang JSON
 * Cho phép XMLDataProcessor hoạt động với DataProcessor interface
 */
public class XMLToJSONAdapter implements DataProcessor {
    private XMLDataProcessor xmlProcessor;
    
    public XMLToJSONAdapter(XMLDataProcessor xmlProcessor) {
        this.xmlProcessor = xmlProcessor;
    }
    
    @Override
    public String processData(String data) {
        System.out.println("🔧 Adapter: Nhận dữ liệu để xử lý...");
        
        // Kiểm tra xem dữ liệu có phải XML không
        if (data.trim().startsWith("<") && data.trim().endsWith(">")) {
            System.out.println("Adapter: Phát hiện dữ liệu XML, đang chuyển đổi...");
            
            // Chuyển đổi XML sang JSON
            String jsonData = xmlProcessor.convertToJSON(data);
            System.out.println("✅ Adapter: Đã chuyển đổi thành JSON: " + jsonData);
            
            // Xử lý dữ liệu JSON đã chuyển đổi
            JSONDataProcessor jsonProcessor = new JSONDataProcessor();
            return jsonProcessor.processData(jsonData);
            
        } else if (data.trim().startsWith("{") && data.trim().endsWith("}")) {
            System.out.println("Adapter: Dữ liệu đã là JSON, xử lý trực tiếp...");
            JSONDataProcessor jsonProcessor = new JSONDataProcessor();
            return jsonProcessor.processData(data);
            
        } else {
            System.out.println("❌ Adapter: Không thể xác định định dạng dữ liệu");
            return "Error: Unsupported data format";
        }
    }
    
    public String convertXMLToJSON(String xmlData) {
        return xmlProcessor.convertToJSON(xmlData);
    }
}