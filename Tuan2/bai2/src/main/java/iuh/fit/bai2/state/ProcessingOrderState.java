package iuh.fit.bai2.state;

import iuh.fit.bai2.context.OrderContext;

// State Pattern - Trạng thái "Đang xử lý"
public class ProcessingOrderState implements OrderState {
    
    @Override
    public void processOrder(OrderContext context) {
        System.out.println("Dong goi san pham cho don hang: " + context.getOrder().getOrderId());
        System.out.println("Chuan bi van chuyen");
        System.out.println("Tao ma van don");
        System.out.println("-> Don hang dang duoc xu ly...");
    }
    
    @Override
    public void cancelOrder(OrderContext context) {
        System.out.println("Dung xu ly don hang: " + context.getOrder().getOrderId());
        System.out.println("Hoan tra san pham ve kho");
        context.setState(new CancelledOrderState());
        System.out.println("-> Don hang da duoc huy");
    }
    
    @Override
    public void deliverOrder(OrderContext context) {
        System.out.println("Giao hang thanh cong cho don hang: " + context.getOrder().getOrderId());
        System.out.println("Khach hang da nhan duoc hang");
        context.setState(new DeliveredOrderState());
        System.out.println("-> Don hang chuyen sang trang thai: " + context.getState().getStateName());
    }
    
    @Override
    public String getStateName() {
        return "Dang xu ly";
    }
}