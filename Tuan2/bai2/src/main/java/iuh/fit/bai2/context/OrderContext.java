package iuh.fit.bai2.context;

import iuh.fit.bai2.model.Order;
import iuh.fit.bai2.state.OrderState;
import iuh.fit.bai2.state.NewOrderState;

// State Pattern - Context class quan ly trang thai don hang
public class OrderContext {
    private Order order;
    private OrderState state;
    
    public OrderContext(Order order) {
        this.order = order;
        this.state = new NewOrderState(); // Trang thai mac dinh
    }
    
    public void processOrder() {
        System.out.println("\n=== XU LY DON HANG ===");
        System.out.println("Trang thai hien tai: " + state.getStateName());
        state.processOrder(this);
    }
    
    public void cancelOrder() {
        System.out.println("\n=== HUY DON HANG ===");
        System.out.println("Trang thai hien tai: " + state.getStateName());
        state.cancelOrder(this);
    }
    
    public void deliverOrder() {
        System.out.println("\n=== GIAO DON HANG ===");
        System.out.println("Trang thai hien tai: " + state.getStateName());
        state.deliverOrder(this);
    }
    
    // Getters and Setters
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    
    public OrderState getState() { return state; }
    public void setState(OrderState state) { this.state = state; }
    
    public String getCurrentStateName() {
        return state.getStateName();
    }
}