package iuh.fit.bai2.decorator;

// Decorator Pattern - Abstract Decorator
public abstract class OrderDecorator implements OrderComponent {
    protected OrderComponent orderComponent;
    
    public OrderDecorator(OrderComponent orderComponent) {
        this.orderComponent = orderComponent;
    }
    
    @Override
    public String getDescription() {
        return orderComponent.getDescription();
    }
    
    @Override
    public double getCost() {
        return orderComponent.getCost();
    }
}