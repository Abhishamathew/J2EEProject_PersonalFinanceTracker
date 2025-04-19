package com.rm.project.controller;

import com.rm.project.model.Budget;
import com.rm.project.service.BudgetService;
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
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Tag(name = "Budget APIs", description = "APIs for managing budgets")
@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @Operation(summary = "Get all budgets by user ID",
            description = "Retrieve all budgets associated with a specific user ID")
    @GetMapping("/user/{userId}")
    public List<Budget> getBudgetsByUser(@PathVariable Long userId) {
        return budgetService.getBudgetsByUserId(userId);
    }

    @Operation(summary = "Get a budget by ID",
            description = "Retrieve a specific budget by its ID")
    @GetMapping("/{budgetId}")
    public Budget getBudgetById(@PathVariable Long budgetId) {
        return budgetService.getBudgetById(budgetId).orElse(null);
    }

    @Operation(summary = "Create a new budget",
            description = "Create a new budget for the user with the specified details")
    @PostMapping
    public Budget createBudget(
            @RequestParam double amount,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID cannot be null");
        }

        if (amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be greater than 0");
        }

        if(startDate == null || endDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date and end date cannot be null");
        }
        if(Date.valueOf(startDate).after(Date.valueOf(endDate))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be after end date");
        }

        Budget budget = new Budget();
        budget.setAmount(amount);
        budget.setExpenditure(0);
        budget.setStartDate(Date.valueOf(startDate));
        budget.setEndDate(Date.valueOf(endDate));
        budget.setUserId(userId);

        try{
            return budgetService.saveBudget(budget);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Argument: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating budget", e);
        }
    }

    @Operation(summary = "Update an existing budget",
            description = "Update the details of an existing budget")
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

    @Operation(summary = "Delete a budget by ID",
            description = "Delete a specific budget by its ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Budget deleted successfully",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Budget deleted"))),
                    @ApiResponse(responseCode = "404", description = "Budget not found",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Budget not found")))
            }
    )
    @DeleteMapping("/{budgetId}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long budgetId) {
        Optional<Budget> budgetOpt = budgetService.getBudgetById(budgetId);
        if(budgetOpt.isPresent()) {
            budgetService.deleteBudget(budgetOpt.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Budget not found");
    }
}