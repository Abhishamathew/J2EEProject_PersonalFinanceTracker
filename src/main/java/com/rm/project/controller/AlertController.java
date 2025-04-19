package com.rm.project.controller;

import com.rm.project.model.Alert;
import com.rm.project.service.AlertService;
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

@Tag(name = "Alert APIs", description = "APIs for managing budget alerts")
@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    @Autowired
    private AlertService alertService;

    @Operation(summary = "Get all alerts by budget ID",
            description = "Retrieve all alerts associated with a specific budget ID")
    @GetMapping("/budget/{budgetId}")
    public List<Alert> getAlertsByBudget(@PathVariable Long budgetId) {
        return alertService.getAltersByBudgetId(budgetId);
    }

    @Operation(summary = "Get an alert by ID",
            description = "Retrieve a specific alert by its ID")
    @GetMapping("/{alertId}")
    public Alert getGoalById(@PathVariable Long alertId) {
        Alert alert = alertService.getAlertById(alertId);
        if (alert == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found");
        }
        return alert;
    }

    @Operation(summary = "Create a new alert",
            description = "Create a new alert with the specified details")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alert createAlert(
            @RequestParam String description,
            @RequestParam double targetAmount,
            @RequestParam(defaultValue = "0.0") double currentAmount,
            @RequestParam String deadline,
            @RequestParam Long budgetId) {

        Alert alert =
                new Alert();
        alert.setDescription(description);
        alert.setTargetAmount(targetAmount);
        alert.setCurrentAmount(currentAmount);
        alert.setDeadline(Date.valueOf(deadline));
        alert.setBudgetId(budgetId);
        try {
            return alertService.saveAlert(alert);
        }catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Argument: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating alert");
        }
    }

    @Operation(summary = "Update an existing alert",
            description = "Update the details of an existing alert")
    @PutMapping("/{alertId}")
    public Alert updateAlert(
            @PathVariable Long alertId,
            @RequestParam String description,
            @RequestParam double targetAmount,
            @RequestParam double currentAmount,
            @RequestParam String deadline) {

        Alert alert = alertService.getAlertById(alertId);
        if (alert == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found");
        }

        alert.setDescription(description);
        alert.setTargetAmount(targetAmount);
        alert.setCurrentAmount(currentAmount);
        alert.setDeadline(Date.valueOf(deadline));

        return alertService.saveAlert(alert);
    }

    @Operation(summary = "Delete an alert by ID",
            description = "Delete a specific alert by its ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Alert deleted successfully",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Alert deleted"))),
                    @ApiResponse(responseCode = "404", description = "Alert not found",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Alert not found")))
            }
    )
    @DeleteMapping("/{alertId}")
    public ResponseEntity<String> deleteAlert(@PathVariable Long alertId) {
        Alert alert = alertService.getAlertById(alertId);
        if (alert == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alert not found");
        }
        alertService.deleteAlert(alert);
        return ResponseEntity.noContent().build();
    }
}