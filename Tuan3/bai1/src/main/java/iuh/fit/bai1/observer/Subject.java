package iuh.fit.bai1.observer;

/**
 * Subject interface trong Observer Pattern
 * Định nghĩa các phương thức quản lý Observer
 */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}