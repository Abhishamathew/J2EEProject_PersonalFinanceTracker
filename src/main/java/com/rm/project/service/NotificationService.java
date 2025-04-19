package com.rm.project.service;

import com.rm.project.model.Notification;
import com.rm.project.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    public List<Notification> saveNotifications(List<Notification> notifications) {
        return notificationRepository.saveAll(notifications);
    }

    public Notification saveNotification(Notification notification) {
        userService.getUserById(notification.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Notification notification) {
        notificationRepository.delete(notification);
    }
}
