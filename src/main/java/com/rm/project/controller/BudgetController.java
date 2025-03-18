package com.rm.project.controller;

import com.rm.project.model.Budget;
import com.rm.project.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @GetMapping("/user/{userId}")
    public List<Budget> getBudgetsByUser(@PathVariable Long userId) {
        return budgetService.getBudgetsByUserId(userId);
    }

    @GetMapping("/{budgetId}")
    public Budget getBudgetById(@PathVariable Long budgetId) {
        return budgetService.getBudgetById(budgetId).orElse(null);
    }

    @PostMapping
    public Budget createBudget(
            @RequestParam double amount,
            @RequestParam(defaultValue = "0.0") double expenditure,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam Long userId) {

        Budget budget = new Budget();
        budget.setAmount(amount);
        budget.setExpenditure(expenditure);
        budget.setStartDate(Date.valueOf(startDate));
        budget.setEndDate(Date.valueOf(endDate));
        budget.setUserId(userId);

        return budgetService.saveBudget(budget);
    }

    @PutMapping("/{budgetId}")
    public Budget updateBudget(
            @PathVariable Long budgetId,
            @RequestParam double amount,
            @RequestParam double expenditure,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        Budget budget = budgetService.getBudgetById(budgetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Budget not found"));

        budget.setAmount(amount);
        budget.setExpenditure(expenditure);
        budget.setStartDate(Date.valueOf(startDate));
        budget.setEndDate(Date.valueOf(endDate));

        return budgetService.saveBudget(budget);
    }

    @DeleteMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudget(@PathVariable Long budgetId) {
        Budget budget = budgetService.getBudgetById(budgetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Budget not found"));

        budgetService.deleteBudget(budget);
    }
}