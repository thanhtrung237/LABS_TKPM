package iuh.fit.bai2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String customerName;
    private List<String> items;
    private double totalAmount;
    private LocalDateTime createdDate;
    
    public Order(String orderId, String customerName, double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.items = new ArrayList<>();
        this.createdDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public List<String> getItems() { return items; }
    public void setItems(List<String> items) { this.items = items; }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    
    public void addItem(String item) {
        this.items.add(item);
    }
    
    @Override
    public String toString() {
        return String.format("Order[ID=%s, Customer=%s, Amount=%.2f, Items=%s]", 
                           orderId, customerName, totalAmount, items);
    }
}