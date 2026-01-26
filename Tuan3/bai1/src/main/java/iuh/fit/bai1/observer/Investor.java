package iuh.fit.bai1.observer;

/**
 * Concrete Observer - Nhà đầu tư theo dõi giá cổ phiếu
 */
public class Investor implements Observer {
    private String name;
    private String strategy;
    
    public Investor(String name, String strategy) {
        this.name = name;
        this.strategy = strategy;
    }
    
    @Override
    public void update(Subject subject) {
        if (subject instanceof StockPrice) {
            StockPrice stock = (StockPrice) subject;
            System.out.println("Nhà đầu tư " + name + " nhận thông báo:");
            System.out.println("   Cổ phiếu " + stock.getSymbol() + " hiện tại: $" + stock.getPrice());
            System.out.println("   Chiến lược: " + strategy);
            
            // Logic đơn giản cho demo
            if (stock.getPrice() > 100) {
                System.out.println("   Quyết định: BÁN");
            } else {
                System.out.println("   Quyết định: MUA");
            }
        }
    }
    
    public String getName() {
        return name;
    }
}