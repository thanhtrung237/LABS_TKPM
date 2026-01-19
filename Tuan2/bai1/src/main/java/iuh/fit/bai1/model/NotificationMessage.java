package iuh.fit.bai1.model;

import java.time.LocalDateTime;

/**
 * Model đại diện cho một thông báo
 */
public class NotificationMessage {
    private String recipient;
    private String subject;
    private String content;
    private LocalDateTime timestamp;
    private String priority; // HIGH, MEDIUM, LOW

    public NotificationMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public NotificationMessage(String recipient, String subject, String content, String priority) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.priority = priority;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", priority='" + priority + '\'' +
                '}';
    }
}