package iuh.fit.bai2.decorator;

import iuh.fit.bai2.model.Order;

// Decorator Pattern - Concrete Component (đơn hàng cơ bản)
public class BasicOrder implements OrderComponent {
    private Order order;
    
    public BasicOrder(Order order) {
        this.order = order;
    }
    
    @Override
    public String getDescription() {
        return "Don hang co ban: " + order.toString();
    }
    
    @Override
    public double getCost() {
        return order.getTotalAmount();
    }
    
    public Order getOrder() {
        return order;
    }
}