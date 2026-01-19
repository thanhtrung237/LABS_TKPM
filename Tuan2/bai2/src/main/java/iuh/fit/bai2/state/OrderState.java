package iuh.fit.bai2.state;

import iuh.fit.bai2.context.OrderContext;

// State Pattern - Interface cho các trạng thái đơn hàng
public interface OrderState {
    void processOrder(OrderContext context);
    void cancelOrder(OrderContext context);
    void deliverOrder(OrderContext context);
    String getStateName();
}