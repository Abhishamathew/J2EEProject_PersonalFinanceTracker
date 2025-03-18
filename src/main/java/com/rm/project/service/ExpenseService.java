package com.rm.project.service;

import com.rm.project.model.Budget;
import com.rm.project.model.Expense;
import com.rm.project.model.Alert;
import com.rm.project.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private AlertService alertService;

    public List<Expense> getExpensesByBudgetId(Long budgetId) {
        return expenseRepository.findByBudgetId(budgetId);
    }

    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).orElse(null);
    }

    public Expense saveExpense(Expense expense) {
        Budget budget = budgetService.getBudgetById(expense.getBudgetId())
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));

        double updateAmount = expense.getAmount();
        if (expense.getExpenseId() != null) {
            Expense oldExpense = expenseRepository.findById(expense.getExpenseId()).orElse(null);
            if (oldExpense != null) {
                updateAmount -= oldExpense.getAmount();
            }
        }
        budget.setExpenditure(budget.getExpenditure() + updateAmount);
        List<Alert> alerts = alertService.getAltersByBudgetId(budget.getBudgetId());
        for (Alert alert : alerts) {
            if (expense.getDate().before(alert.getDeadline()) || expense.getDate().equals(alert.getDeadline())) {
                alert.setCurrentAmount(alert.getCurrentAmount() + updateAmount);
            }
        }
        alertService.saveAlerts(alerts);
        budgetService.saveBudget(budget);
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElse(null);
        if (expense != null) {
            Budget budget = budgetService.getBudgetById(expense.getBudgetId())
                    .orElseThrow(() -> new IllegalArgumentException("Budget not found"));

            budget.setExpenditure(budget.getExpenditure() - expense.getAmount());
            budgetService.saveBudget(budget);
            expenseRepository.deleteById(expenseId);
        }

    }
}
