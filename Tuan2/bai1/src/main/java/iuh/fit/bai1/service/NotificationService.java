package iuh.fit.bai1.service;

import iuh.fit.bai1.model.NotificationMessage;

/**
 * Interface cho các dịch vụ thông báo
 */
public interface NotificationService {
    boolean sendNotification(NotificationMessage message);
    String getServiceType();
    boolean isAvailable();
}