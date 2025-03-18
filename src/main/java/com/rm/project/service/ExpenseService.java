package com.rm.project.service;

import com.rm.project.model.Budget;
import com.rm.project.model.Expense;
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

    public List<Expense> getExpensesByBudgetId(Long budgetId) {
        return expenseRepository.findByBudgetId(budgetId);
    }

    public Expense saveExpense(Expense expense) {
        Budget budget = budgetService.getBudgetById(expense.getBudgetId()).orElse(null);
        assert budget != null;
        if(expense.getExpenseId() != null) {
            expenseRepository.findById(expense.getExpenseId()).ifPresent(oldExp -> budget.setExpenditure(budget.getExpenditure() - oldExp.getAmount()));
        }
        budget.setExpenditure(budget.getExpenditure() + expense.getAmount());

        budgetService.saveBudget(budget);
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElse(null);
        if(expense != null) {
            Budget budget = budgetService.getBudgetById(expense.getBudgetId()).orElse(null);
            assert budget != null;
            budget.setExpenditure(budget.getExpenditure() - expense.getAmount());
            budgetService.saveBudget(budget);
            expenseRepository.deleteById(expenseId);
        }

    }
}
