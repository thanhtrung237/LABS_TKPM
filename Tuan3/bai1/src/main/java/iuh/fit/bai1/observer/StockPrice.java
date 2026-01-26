package iuh.fit.bai1.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Subject cho việc theo dõi giá cổ phiếu
 */
public class StockPrice implements Subject {
    private String symbol;
    private double price;
    private List<Observer> observers;
    
    public StockPrice(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
        this.observers = new ArrayList<>();
    }
    
    public void setPrice(double newPrice) {
        if (this.price != newPrice) {
            this.price = newPrice;
            notifyObservers();
        }
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Observer đã đăng ký theo dõi cổ phiếu " + symbol);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer đã hủy theo dõi cổ phiếu " + symbol);
    }
    
    @Override
    public void notifyObservers() {
        System.out.println("Giá cổ phiếu " + symbol + " thay đổi: $" + price);
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}