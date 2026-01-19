package iuh.fit.bai2.state;

import iuh.fit.bai2.context.OrderContext;

// State Pattern - Trạng thái "Đã giao"
public class DeliveredOrderState implements OrderState {
    
    @Override
    public void processOrder(OrderContext context) {
        System.out.println("Don hang da duoc giao, khong the xu ly lai.");
    }
    
    @Override
    public void cancelOrder(OrderContext context) {
        System.out.println("Khong the huy don hang da duoc giao. Vui long lien he bo phan ho tro de doi tra.");
    }
    
    @Override
    public void deliverOrder(OrderContext context) {
        System.out.println("Cap nhat trang thai don hang: " + context.getOrder().getOrderId());
        System.out.println("Don hang da duoc giao thanh cong");
        System.out.println("Gui email xac nhan giao hang cho khach hang");
    }
    
    @Override
    public String getStateName() {
        return "Da giao";
    }
}