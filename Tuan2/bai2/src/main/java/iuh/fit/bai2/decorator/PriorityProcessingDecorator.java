package iuh.fit.bai2.decorator;

// Decorator Pattern - Concrete Decorator (Xử lý ưu tiên)
public class PriorityProcessingDecorator extends OrderDecorator {
    
    public PriorityProcessingDecorator(OrderComponent orderComponent) {
        super(orderComponent);
    }
    
    @Override
    public String getDescription() {
        return orderComponent.getDescription() + " + Xu ly uu tien";
    }
    
    @Override
    public double getCost() {
        return orderComponent.getCost() + getPriorityProcessingCost();
    }
    
    private double getPriorityProcessingCost() {
        return 50000; // Phi xu ly uu tien co dinh 50,000 VND
    }
    
    public void processPriority() {
        System.out.println("Da them dich vu xu ly uu tien");
        System.out.println("Don hang se duoc xu ly truoc cac don hang khac");
        System.out.println("Phi xu ly uu tien: " + String.format("%.0f VND", getPriorityProcessingCost()));
    }
}