package com.rm.project.service;

import com.rm.project.model.Budget;
import com.rm.project.model.Notification;
import com.rm.project.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Budget> getBudgetsByUserId(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    public Optional<Budget> getBudgetById(Long budgetId) {
        return budgetRepository.findById(budgetId);
    }

    public Budget saveBudget(Budget budget) {
        if(budget.getAmount() <= budget.getExpenditure()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String message = String.format("Budget for period %s to %s has exceeded",
                    dateFormat.format(budget.getStartDate()),
                    dateFormat.format(budget.getEndDate()));
            Notification notification = new Notification( message, new Date(System.currentTimeMillis()) , budget.getUserId());
            notificationService.saveNotification(notification);
        }
        return budgetRepository.save(budget);
    }

    public void deleteBudget(Budget budget) {
        budgetRepository.delete(budget);
    }
}
