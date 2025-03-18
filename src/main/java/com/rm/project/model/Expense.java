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
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    private double amount;
    private String category;
    private Date date;

    private Long budgetId;

    public Expense(double amount, String category, Date date, Long budgetId) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.budgetId = budgetId;
    }
}
