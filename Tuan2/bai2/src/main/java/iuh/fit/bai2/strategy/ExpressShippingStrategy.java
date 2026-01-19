package iuh.fit.bai2.strategy;

import iuh.fit.bai2.model.Order;

// Strategy Pattern - Chiến lược vận chuyển nhanh
public class ExpressShippingStrategy implements ShippingStrategy {
    
    @Override
    public double calculateShippingCost(Order order) {
        // Phí vận chuyển = 5% giá trị đơn hàng, tối thiểu 50,000 VND
        double cost = order.getTotalAmount() * 0.05;
        return Math.max(cost, 50000);
    }
    
    @Override
    public int getEstimatedDeliveryDays() {
        return 2; // 2 ngày
    }
    
    @Override
    public String getShippingMethod() {
        return "Van chuyen nhanh";
    }
}