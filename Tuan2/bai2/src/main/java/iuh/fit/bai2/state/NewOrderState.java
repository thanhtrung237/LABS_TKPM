package iuh.fit.bai2.state;

import iuh.fit.bai2.context.OrderContext;

// State Pattern - Trạng thái "Mới tạo"
public class NewOrderState implements OrderState {
    
    @Override
    public void processOrder(OrderContext context) {
        System.out.println("Kiem tra thong tin don hang: " + context.getOrder().getOrderId());
        System.out.println("Xac thuc thong tin khach hang: " + context.getOrder().getCustomerName());
        System.out.println("Kiem tra ton kho cho cac san pham");
        
        // Chuyen sang trang thai "Dang xu ly"
        context.setState(new ProcessingOrderState());
        System.out.println("-> Don hang chuyen sang trang thai: " + context.getState().getStateName());
    }
    
    @Override
    public void cancelOrder(OrderContext context) {
        System.out.println("Huy don hang: " + context.getOrder().getOrderId());
        context.setState(new CancelledOrderState());
        System.out.println("-> Don hang da duoc huy");
    }
    
    @Override
    public void deliverOrder(OrderContext context) {
        System.out.println("Khong the giao don hang o trang thai 'Moi tao'. Vui long xu ly don hang truoc.");
    }
    
    @Override
    public String getStateName() {
        return "Moi tao";
    }
}