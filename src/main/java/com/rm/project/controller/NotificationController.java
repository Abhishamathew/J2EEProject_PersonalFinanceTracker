package com.rm.project.controller;

import com.rm.project.model.Notification;
import com.rm.project.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUser(@PathVariable Long userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.saveNotification(notification);
    }
}
