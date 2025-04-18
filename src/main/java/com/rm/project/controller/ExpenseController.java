package com.rm.project.controller;

import com.rm.project.model.Expense;
import com.rm.project.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Tag(name = "Expense APIs", description = "APIs for managing the budget expenses")
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @Operation(summary = "Get all expenses by budget ID",
            description = "Retrieve all expenses associated with a specific budget ID")
    @GetMapping("/budget/{budgetId}")
    public List<Expense> getExpensesByBudget(@PathVariable Long budgetId) {
        return expenseService.getExpensesByBudgetId(budgetId);
    }

    @Operation(summary = "Create a new expense",
            description = "Create a new expense with the specified details")
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

    @Operation(summary = "Get an expense by ID",
            description = "Retrieve a specific expense by its ID")
    @GetMapping("/{expenseId}")
    public Expense getExpenseById(@PathVariable Long expenseId) {
        return expenseService.getExpenseById(expenseId);
    }

    @Operation(summary = "Update an existing expense",
            description = "Update the details of an existing expense")
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

    @Operation(summary = "Delete an expense by ID",
            description = "Delete a specific expense by its ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Expense deleted successfully",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Expense deleted"))),
                    @ApiResponse(responseCode = "404", description = "Expense not found",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Expense not found")))
            }
    )
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long expenseId) {
        Expense expense = expenseService.getExpenseById(expenseId);
        if(expense != null) {
            expenseService.deleteExpense(expense.getExpenseId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
    }
}