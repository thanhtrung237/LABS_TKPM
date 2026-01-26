package iuh.fit.bai1.observer;

/**
 * Observer interface trong Observer Pattern
 * Định nghĩa phương thức nhận thông báo từ Subject
 */
public interface Observer {
    void update(Subject subject);
}