package iuh.fit.bai2.strategy;

import iuh.fit.bai2.model.Order;

// Strategy Pattern - Context class quản lý chiến lược vận chuyển
public class ShippingContext {
    private ShippingStrategy shippingStrategy;
    
    public ShippingContext(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
    }
    
    public void setShippingStrategy(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
    }
    
    public void executeShipping(Order order) {
        System.out.println("\n=== THONG TIN VAN CHUYEN ===");
        System.out.println("Phuong thuc: " + shippingStrategy.getShippingMethod());
        System.out.println("Phi van chuyen: " + String.format("%.0f VND", shippingStrategy.calculateShippingCost(order)));
        System.out.println("Thoi gian giao hang du kien: " + shippingStrategy.getEstimatedDeliveryDays() + " ngay");
        System.out.println("Tong tien (bao gom phi ship): " + 
                         String.format("%.0f VND", order.getTotalAmount() + shippingStrategy.calculateShippingCost(order)));
    }
    
    public double getTotalCost(Order order) {
        return order.getTotalAmount() + shippingStrategy.calculateShippingCost(order);
    }
}