package iuh.fit.bai2.strategy;

import iuh.fit.bai2.model.Order;

// Strategy Pattern - Interface cho các chiến lược vận chuyển
public interface ShippingStrategy {
    double calculateShippingCost(Order order);
    int getEstimatedDeliveryDays();
    String getShippingMethod();
}