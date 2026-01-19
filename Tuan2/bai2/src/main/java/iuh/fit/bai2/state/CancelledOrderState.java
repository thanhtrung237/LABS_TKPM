package iuh.fit.bai2.state;

import iuh.fit.bai2.context.OrderContext;

// State Pattern - Trạng thái "Hủy"
public class CancelledOrderState implements OrderState {
    
    @Override
    public void processOrder(OrderContext context) {
        System.out.println("Khong the xu ly don hang da bi huy.");
    }
    
    @Override
    public void cancelOrder(OrderContext context) {
        System.out.println("Xu ly hoan tien cho don hang: " + context.getOrder().getOrderId());
        System.out.println("So tien hoan: " + context.getOrder().getTotalAmount() + " VND");
        System.out.println("Gui thong bao huy don hang cho khach hang");
        System.out.println("-> Don hang da duoc huy hoan toan");
    }
    
    @Override
    public void deliverOrder(OrderContext context) {
        System.out.println("Khong the giao don hang da bi huy.");
    }
    
    @Override
    public String getStateName() {
        return "Da huy";
    }
}