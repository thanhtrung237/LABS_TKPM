package iuh.fit.bai1.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Subject cho việc theo dõi trạng thái công việc
 */
public class TaskStatus implements Subject {
    private String taskName;
    private String status;
    private String assignee;
    private List<Observer> observers;
    
    public TaskStatus(String taskName, String initialStatus, String assignee) {
        this.taskName = taskName;
        this.status = initialStatus;
        this.assignee = assignee;
        this.observers = new ArrayList<>();
    }
    
    public void setStatus(String newStatus) {
        if (!this.status.equals(newStatus)) {
            String oldStatus = this.status;
            this.status = newStatus;
            System.out.println("Task '" + taskName + "' chuyển từ '" + oldStatus + "' sang '" + newStatus + "'");
            notifyObservers();
        }
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getAssignee() {
        return assignee;
    }
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Observer đã đăng ký theo dõi task: " + taskName);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer đã hủy theo dõi task: " + taskName);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}