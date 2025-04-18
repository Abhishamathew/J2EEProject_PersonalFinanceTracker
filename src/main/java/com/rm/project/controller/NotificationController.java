package com.rm.project.controller;

import com.rm.project.model.Notification;
import com.rm.project.service.NotificationService;
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

import java.util.List;

@Tag(name = "Notification APIs", description = "APIs for managing user notifications")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Get all notifications by user ID",
            description = "Retrieve all notifications associated with a specific user ID")
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUser(@PathVariable Long userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    @Operation(summary = "Create a new notification",
            description = "Create a new notification with the specified details")
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.saveNotification(notification);
    }

    @Operation(summary = "Delete a notification by ID",
            description = "Delete a specific notification by its ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Notification deleted successfully",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Notification deleted"))),
                    @ApiResponse(responseCode = "404", description = "Notification not found",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Notification not found")))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        if(notification != null) {
            notificationService.deleteNotification(notification);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
    }
}
