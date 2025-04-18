package com.rm.project.service;

import com.rm.project.model.Alert;
import com.rm.project.model.Budget;
import com.rm.project.model.Notification;
import com.rm.project.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private BudgetService budgetService;

    public List<Alert> getAltersByBudgetId(Long budgetId) {
        return alertRepository.findByBudgetId(budgetId);
    }

    public Alert getAlertById(Long alertId) {
        return alertRepository.findById(alertId).orElse(null);
    }

    public Alert saveAlert(Alert alert) {
        Budget budget = budgetService.getBudgetById(alert.getBudgetId())
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        Notification notification = createNotification(alert, budget.getUserId());
        if (notification != null) {
            notificationService.saveNotification(notification);
        }
        return alertRepository.save(alert);
    }

    public void saveAlerts(List<Alert> alerts) {
        if (alerts.isEmpty()) {
            return;
        }
        Budget budget = budgetService.getBudgetById(alerts.getFirst().getBudgetId()).orElse(null);
        assert budget != null;
        List<Notification> notifications = new ArrayList<>();
        for (Alert alert : alerts) {
            Notification notification = createNotification(alert, budget.getUserId());
            if (notification != null) {
                notifications.add(notification);
            }
        }
        notificationService.saveNotifications(notifications);
        alertRepository.saveAll(alerts);
    }

    public void deleteAlert(Alert alert) {
        alertRepository.delete(alert);
    }

    private Notification createNotification(Alert alert, Long userId) {
        if (alert.getTargetAmount() <= alert.getCurrentAmount()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String message = String.format("%s - Alert with deadline - %s was triggered",
                    alert.getDescription(),
                    dateFormat.format(alert.getDeadline()));
            Notification notification = new Notification(message, new Date(System.currentTimeMillis()), userId);
            return notification;
        }
        return null;
    }
}
