package iuh.fit.bai2.strategy;

import iuh.fit.bai2.model.Order;

// Strategy Pattern - Chiến lược vận chuyển cao cấp
public class PremiumShippingStrategy implements ShippingStrategy {
    
    @Override
    public double calculateShippingCost(Order order) {
        // Mien phi van chuyen cho don hang tren 1,000,000 VND
        if (order.getTotalAmount() >= 1000000) {
            return 0;
        }
        // Phi van chuyen = 8% gia tri don hang
        return order.getTotalAmount() * 0.08;
    }
    
    @Override
    public int getEstimatedDeliveryDays() {
        return 1; // 1 ngay
    }
    
    @Override
    public String getShippingMethod() {
        return "Van chuyen cao cap (Same-day delivery)";
    }
}