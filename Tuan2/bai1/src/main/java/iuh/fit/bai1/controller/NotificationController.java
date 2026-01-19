package iuh.fit.bai1.controller;

import iuh.fit.bai1.model.NotificationMessage;
import iuh.fit.bai1.service.NotificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller để test hệ thống notification
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    private final NotificationManager notificationManager;
    
    @Autowired
    public NotificationController(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }
    
    /**
     * Gửi notification đơn lẻ
     */
    @PostMapping("/send/{type}")
    public ResponseEntity<Map<String, Object>> sendNotification(
            @PathVariable String type,
            @RequestBody NotificationMessage message) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean result = notificationManager.sendNotification(type, message);
            
            response.put("success", result);
            response.put("type", type);
            response.put("message", result ? "Notification sent successfully" : "Failed to send notification");
            response.put("recipient", message.getRecipient());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Gửi notification với environment-specific factory
     */
    @PostMapping("/send-env/{type}")
    public ResponseEntity<Map<String, Object>> sendNotificationWithEnvironment(
            @PathVariable String type,
            @RequestBody NotificationMessage message) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean result = notificationManager.sendNotificationWithEnvironment(type, message);
            
            response.put("success", result);
            response.put("type", type);
            response.put("message", result ? "Environment notification sent successfully" : "Failed to send notification");
            response.put("recipient", message.getRecipient());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Gửi batch notification
     */
    @PostMapping("/send-batch/{type}")
    public ResponseEntity<Map<String, Object>> sendBatchNotification(
            @PathVariable String type,
            @RequestBody NotificationMessage message) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean result = notificationManager.sendBatchNotification(type, message);
            
            response.put("success", result);
            response.put("type", "BATCH_" + type);
            response.put("message", result ? "Batch notification sent successfully" : "Failed to send batch notification");
            response.put("recipient", message.getRecipient());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Gửi multi-channel notification
     */
    @PostMapping("/send-multi")
    public ResponseEntity<Map<String, Object>> sendMultiChannelNotification(
            @RequestBody NotificationMessage message,
            @RequestParam String[] types) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Boolean> results = notificationManager.sendMultiChannelNotification(message, types);
            
            long successCount = results.stream().mapToLong(r -> r ? 1 : 0).sum();
            
            response.put("totalChannels", types.length);
            response.put("successCount", successCount);
            response.put("failureCount", types.length - successCount);
            response.put("results", results);
            response.put("channels", types);
            response.put("message", "Multi-channel notification completed");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Gửi notification với fallback
     */
    @PostMapping("/send-fallback")
    public ResponseEntity<Map<String, Object>> sendNotificationWithFallback(
            @RequestBody NotificationMessage message,
            @RequestParam String primaryType,
            @RequestParam String fallbackType) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean result = notificationManager.sendNotificationWithFallback(message, primaryType, fallbackType);
            
            response.put("success", result);
            response.put("primaryType", primaryType);
            response.put("fallbackType", fallbackType);
            response.put("message", result ? "Notification sent successfully" : "Both primary and fallback failed");
            response.put("recipient", message.getRecipient());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Lấy thông tin cấu hình hệ thống
     */
    @GetMapping("/config")
    public ResponseEntity<String> getSystemConfiguration() {
        notificationManager.displaySystemConfiguration();
        return ResponseEntity.ok("System configuration displayed in console");
    }
}