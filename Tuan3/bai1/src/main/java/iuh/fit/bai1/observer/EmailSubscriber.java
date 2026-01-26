package iuh.fit.bai1.observer;

/**
 * Email Subscriber - Observer nhận thông báo qua email
 */
public class EmailSubscriber implements Observer {
    private String email;
    private String name;
    private boolean notified = false;
    private String lastNotification = "";
    
    public EmailSubscriber(String email, String name) {
        this.email = email;
        this.name = name;
    }
    
    @Override
    public void update(Subject subject) {
        this.notified = true;
        
        if (subject instanceof StockPrice) {
            StockPrice stock = (StockPrice) subject;
            this.lastNotification = String.format(
                "📧 Email sent to %s (%s):\n" +
                "Subject: Stock Price Alert - %s\n" +
                "Dear %s,\n" +
                "The stock %s has changed to $%.2f\n" +
                "Best regards,\n" +
                "Stock Alert System",
                email, name, stock.getSymbol(), name, stock.getSymbol(), stock.getPrice()
            );
        } else if (subject instanceof TaskStatus) {
            TaskStatus task = (TaskStatus) subject;
            this.lastNotification = String.format(
                "📧 Email sent to %s (%s):\n" +
                "Subject: Task Status Update - %s\n" +
                "Dear %s,\n" +
                "Task '%s' status changed to: %s\n" +
                "Assignee: %s\n" +
                "Best regards,\n" +
                "Project Management System",
                email, name, task.getTaskName(), name, task.getTaskName(), task.getStatus(), task.getAssignee()
            );
        }
    }
    
    public boolean wasNotified() {
        return notified;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
    public String getLastNotification() {
        return lastNotification;
    }
    
    public void reset() {
        this.notified = false;
        this.lastNotification = "";
    }
    
    @Override
    public String toString() {
        return name + " <" + email + ">";
    }
}