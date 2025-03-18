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
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    private String description;
    private double targetAmount;
    private double currentAmount;
    private Date deadline;

    private Long budgetId;

    public Goal(String description, double targetAmount, double currentAmount, Date deadline) {
        this.description = description;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
    }
}
