package com.rm.project.controller;

import com.rm.project.model.Alert;
import com.rm.project.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    @Autowired
    private AlertService alertService;

    @GetMapping("/budget/{budgetId}")
    public List<Alert> getAlertsByBudget(@PathVariable Long budgetId) {
        return alertService.getAltersByBudgetId(budgetId);
    }

    @GetMapping("/{alertId}")
    public Alert getGoalById(@PathVariable Long alertId) {
        Alert alert = alertService.getAlertById(alertId);
        if (alert == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found");
        }
        return alert;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alert createAlert(
            @RequestParam String description,
            @RequestParam double targetAmount,
            @RequestParam(defaultValue = "0.0") double currentAmount,
            @RequestParam String deadline,
            @RequestParam Long budgetId) {

        Alert alert = new Alert();
        alert.setDescription(description);
        alert.setTargetAmount(targetAmount);
        alert.setCurrentAmount(currentAmount);
        alert.setDeadline(Date.valueOf(deadline));
        alert.setBudgetId(budgetId);

        return alertService.saveAlert(alert);
    }

    @PutMapping("/{alertId}")
    public Alert updateAlert(
            @PathVariable Long alertId,
            @RequestParam String description,
            @RequestParam double targetAmount,
            @RequestParam double currentAmount,
            @RequestParam String deadline,
            @RequestParam Long budgetId) {

        Alert alert = alertService.getAlertById(alertId);
        if (alert == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found");
        }

        alert.setDescription(description);
        alert.setTargetAmount(targetAmount);
        alert.setCurrentAmount(currentAmount);
        alert.setDeadline(Date.valueOf(deadline));
        alert.setBudgetId(budgetId);

        return alertService.saveAlert(alert);
    }

    @DeleteMapping("/{alertId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlert(@PathVariable Long alertId) {
        Alert alert = alertService.getAlertById(alertId);
        if (alert == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found");
        }

        alertService.deleteAlert(alert);
    }
}