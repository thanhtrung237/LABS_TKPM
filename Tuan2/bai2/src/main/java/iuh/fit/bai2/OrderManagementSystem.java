package iuh.fit.bai2;

import iuh.fit.bai2.model.Order;
import iuh.fit.bai2.context.OrderContext;
import iuh.fit.bai2.strategy.*;
import iuh.fit.bai2.decorator.*;

public class OrderManagementSystem {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("    HE THONG QUAN LY DON HANG - DESIGN PATTERNS DEMO");
        System.out.println("=".repeat(60));
        
        // Tao don hang mau
        Order order = new Order("ORD-001", "Nguyen Van A", 800000);
        order.addItem("Laptop Dell");
        order.addItem("Chuot khong day");
        
        System.out.println("\nTHONG TIN DON HANG GOC:");
        System.out.println(order);
        
        // === DEMO STATE PATTERN ===
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           DEMO STATE PATTERN");
        System.out.println("=".repeat(50));
        
        OrderContext orderContext = new OrderContext(order);
        
        // Xu ly don hang qua cac trang thai
        orderContext.processOrder();  // Moi tao -> Dang xu ly
        orderContext.processOrder();  // Dang xu ly (dong goi)
        orderContext.deliverOrder();  // Dang xu ly -> Da giao
        orderContext.deliverOrder();  // Cap nhat trang thai da giao
        
        // === DEMO STRATEGY PATTERN ===
        System.out.println("\n" + "=".repeat(50));
        System.out.println("          DEMO STRATEGY PATTERN");
        System.out.println("=".repeat(50));
        
        // Thu cac chien luoc van chuyen khac nhau
        ShippingContext shippingContext = new ShippingContext(new StandardShippingStrategy());
        shippingContext.executeShipping(order);
        
        shippingContext.setShippingStrategy(new ExpressShippingStrategy());
        shippingContext.executeShipping(order);
        
        shippingContext.setShippingStrategy(new PremiumShippingStrategy());
        shippingContext.executeShipping(order);
        
        // === DEMO DECORATOR PATTERN ===
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         DEMO DECORATOR PATTERN");
        System.out.println("=".repeat(50));
        
        // Tao don hang co ban
        OrderComponent basicOrder = new BasicOrder(order);
        System.out.println("\n" + basicOrder.getDescription());
        System.out.println("Tong tien: " + String.format("%.0f VND", basicOrder.getCost()));
        
        // Them cac dich vu bo sung bang Decorator
        System.out.println("\n--- THEM CAC DICH VU BO SUNG ---");
        
        // Them bao hiem
        OrderComponent orderWithInsurance = new InsuranceDecorator(basicOrder);
        ((InsuranceDecorator) orderWithInsurance).processInsurance();
        
        // Them goi qua
        OrderComponent orderWithGiftWrap = new GiftWrapDecorator(orderWithInsurance);
        ((GiftWrapDecorator) orderWithGiftWrap).processGiftWrap();
        
        // Them xu ly uu tien
        OrderComponent finalOrder = new PriorityProcessingDecorator(orderWithGiftWrap);
        ((PriorityProcessingDecorator) finalOrder).processPriority();
        
        System.out.println("\n" + finalOrder.getDescription());
        System.out.println("Tong tien cuoi cung: " + String.format("%.0f VND", finalOrder.getCost()));
        
        // === DEMO TICH HOP CAC PATTERN ===
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        DEMO TICH HOP CAC PATTERN");
        System.out.println("=".repeat(50));
        
        demonstrateIntegratedSystem();
        
        // === KET LUAN ===
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                KET LUAN");
        System.out.println("=".repeat(50));
        printConclusion();
    }
    
    private static void demonstrateIntegratedSystem() {
        System.out.println("\nMo phong quy trinh hoan chinh voi ca 3 pattern:");
        
        // Tao don hang moi
        Order newOrder = new Order("ORD-002", "Tran Thi B", 1200000);
        newOrder.addItem("iPhone 15");
        newOrder.addItem("Op lung");
        
        // 1. Su dung Decorator de them dich vu
        OrderComponent decoratedOrder = new BasicOrder(newOrder);
        decoratedOrder = new InsuranceDecorator(decoratedOrder);
        decoratedOrder = new PriorityProcessingDecorator(decoratedOrder);
        
        System.out.println("\n1. DECORATOR: Da them bao hiem va xu ly uu tien");
        System.out.println("   Tong tien: " + String.format("%.0f VND", decoratedOrder.getCost()));
        
        // 2. Su dung Strategy de chon phuong thuc van chuyen
        ShippingContext shipping = new ShippingContext(new PremiumShippingStrategy());
        System.out.println("\n2. STRATEGY: Chon van chuyen cao cap");
        shipping.executeShipping(newOrder);
        
        // 3. Su dung State de quan ly trang thai
        OrderContext context = new OrderContext(newOrder);
        System.out.println("\n3. STATE: Quan ly trang thai don hang");
        context.processOrder();
        context.deliverOrder();
        
        System.out.println("\nHoan thanh quy trinh xu ly don hang tich hop!");
    }
    
    private static void printConclusion() {
        System.out.println("\nPHAN TICH VA KET LUAN:");
        System.out.println();
        
        System.out.println("STATE PATTERN:");
        System.out.println("   • Quan ly cac trang thai don hang (Moi tao, Dang xu ly, Da giao, Huy)");
        System.out.println("   • Moi trang thai co hanh vi rieng biet");
        System.out.println("   • De dang them trang thai moi ma khong anh huong code cu");
        System.out.println("   • Tranh duoc cac cau lenh if-else phuc tap");
        System.out.println();
        
        System.out.println("STRATEGY PATTERN:");
        System.out.println("   • Cho phep chon thuat toan van chuyen tai runtime");
        System.out.println("   • De dang them phuong thuc van chuyen moi");
        System.out.println("   • Tach biet logic tinh phi van chuyen");
        System.out.println("   • Tuan thu nguyen tac Open/Closed Principle");
        System.out.println();
        
        System.out.println("DECORATOR PATTERN:");
        System.out.println("   • Them tinh nang moi ma khong thay doi code goc");
        System.out.println("   • Co the ket hop nhieu decorator cung luc");
        System.out.println("   • Linh hoat hon so voi ke thua (inheritance)");
        System.out.println("   • Tuan thu nguyen tac Single Responsibility Principle");
        System.out.println();
        
        System.out.println("TICH HOP CAC PATTERN:");
        System.out.println("   • State Pattern: Quan ly vong doi don hang");
        System.out.println("   • Strategy Pattern: Lua chon phuong thuc van chuyen");
        System.out.println("   • Decorator Pattern: Them dich vu bo sung");
        System.out.println("   • Ket hop tao ra he thong linh hoat va de mo rong");
        System.out.println();
        
        System.out.println("HE THONG DAT DUOC:");
        System.out.println("   - Tinh linh hoat cao");
        System.out.println("   - De bao tri va mo rong");
        System.out.println("   - Code sach va co to chuc");
        System.out.println("   - Tuan thu cac nguyen tac SOLID");
        
        System.out.println("\n" + "=".repeat(60));
    }
}