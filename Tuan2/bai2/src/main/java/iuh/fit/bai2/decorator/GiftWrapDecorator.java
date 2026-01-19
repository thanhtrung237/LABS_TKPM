package iuh.fit.bai2.decorator;

// Decorator Pattern - Concrete Decorator (Gói quà)
public class GiftWrapDecorator extends OrderDecorator {
    
    public GiftWrapDecorator(OrderComponent orderComponent) {
        super(orderComponent);
    }
    
    @Override
    public String getDescription() {
        return orderComponent.getDescription() + " + Goi qua dac biet";
    }
    
    @Override
    public double getCost() {
        return orderComponent.getCost() + getGiftWrapCost();
    }
    
    private double getGiftWrapCost() {
        return 25000; // Phi goi qua co dinh 25,000 VND
    }
    
    public void processGiftWrap() {
        System.out.println("Da them dich vu goi qua");
        System.out.println("Phi goi qua: " + String.format("%.0f VND", getGiftWrapCost()));
    }
}