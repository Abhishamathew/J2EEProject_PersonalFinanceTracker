package com.rm.project.controller;

import com.rm.project.model.Expense;
import com.rm.project.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/budget/{budgetId}")
    public List<Expense> getExpensesByBudget(@PathVariable Long budgetId) {
        return expenseService.getExpensesByBudgetId(budgetId);
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }


}
