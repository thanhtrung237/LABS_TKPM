package iuh.fit.bai1.adapter;

/**
 * Adaptee class - hệ thống hiện có chỉ xử lý XML
 */
public class XMLDataProcessor {
    
    public String processXMLData(String xmlData) {
        System.out.println("🔄 Xử lý dữ liệu XML...");
        
        // Giả lập xử lý XML
        if (xmlData.trim().startsWith("<") && xmlData.trim().endsWith(">")) {
            System.out.println("✅ XML hợp lệ, đang xử lý...");
            return "Processed XML: " + xmlData;
        } else {
            System.out.println("❌ Dữ liệu không phải XML hợp lệ");
            return "Error: Invalid XML format";
        }
    }
    
    public String convertToJSON(String xmlData) {
        System.out.println("🔄 Chuyển đổi XML sang JSON...");
        
        // Giả lập chuyển đổi đơn giản XML -> JSON
        if (xmlData.contains("<name>") && xmlData.contains("</name>")) {
            String name = extractValue(xmlData, "name");
            String age = extractValue(xmlData, "age");
            String city = extractValue(xmlData, "city");
            
            return String.format("{\"name\":\"%s\",\"age\":\"%s\",\"city\":\"%s\"}", 
                                name, age, city);
        }
        
        return "{\"error\":\"Cannot convert XML to JSON\"}";
    }
    
    private String extractValue(String xml, String tag) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        int start = xml.indexOf(startTag);
        int end = xml.indexOf(endTag);
        
        if (start != -1 && end != -1) {
            return xml.substring(start + startTag.length(), end);
        }
        return "";
    }
}