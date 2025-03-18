package com.rm.project.controller;

import com.rm.project.model.Expense;
import com.rm.project.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Expense createExpense(
            @RequestParam double amount,
            @RequestParam String category,
            @RequestParam String date,
            @RequestParam Long budgetId) {

        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(Date.valueOf(date));
        expense.setBudgetId(budgetId);

        return expenseService.saveExpense(expense);
    }

    @GetMapping("/{expenseId}")
    public Expense getExpenseById(@PathVariable Long expenseId) {
        return expenseService.getExpenseById(expenseId);
    }

    @PutMapping("/{expenseId}")
    public Expense updateExpense(
            @PathVariable Long expenseId,
            @RequestParam double amount,
            @RequestParam String category,
            @RequestParam String date,
            @RequestParam Long budgetId) {

        Expense expense = expenseService.getExpenseById(expenseId);

        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(Date.valueOf(date));
        expense.setBudgetId(budgetId);

        return expenseService.saveExpense(expense);
    }

    @DeleteMapping("/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteExpense(@PathVariable Long expenseId) {
        Expense expense = expenseService.getExpenseById(expenseId);
        if( expense != null ) {
            expenseService.deleteExpense(expense.getExpenseId());
            return "Expense deleted";
        }
        return "Expense not found";
    }
}