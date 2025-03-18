package com.rm.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String message;
    private Date date;

    private Long userId;

    public Notification(String message, Date date, Long userId) {
        this.message = message;
        this.date = date;
        this.userId = userId;
    }
}
