package iuh.fit.bai2.strategy;

import iuh.fit.bai2.model.Order;

// Strategy Pattern - Chiến lược vận chuyển tiêu chuẩn
public class StandardShippingStrategy implements ShippingStrategy {
    
    @Override
    public double calculateShippingCost(Order order) {
        // Phí vận chuyển cố định 30,000 VND
        return 30000;
    }
    
    @Override
    public int getEstimatedDeliveryDays() {
        return 5; // 5 ngày
    }
    
    @Override
    public String getShippingMethod() {
        return "Van chuyen tieu chuan";
    }
}