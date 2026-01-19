package iuh.fit.bai2.decorator;

// Decorator Pattern - Concrete Decorator (Bảo hiểm đơn hàng)
public class InsuranceDecorator extends OrderDecorator {
    
    public InsuranceDecorator(OrderComponent orderComponent) {
        super(orderComponent);
    }
    
    @Override
    public String getDescription() {
        return orderComponent.getDescription() + " + Bao hiem don hang";
    }
    
    @Override
    public double getCost() {
        return orderComponent.getCost() + getInsuranceCost();
    }
    
    private double getInsuranceCost() {
        // Phi bao hiem = 2% gia tri don hang
        return orderComponent.getCost() * 0.02;
    }
    
    public void processInsurance() {
        System.out.println("Da them bao hiem cho don hang");
        System.out.println("Phi bao hiem: " + String.format("%.0f VND", getInsuranceCost()));
    }
}