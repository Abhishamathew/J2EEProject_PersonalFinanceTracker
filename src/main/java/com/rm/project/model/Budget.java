package com.rm.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    private double amount;
    private double expenditure = 0;
    private Date startDate;
    private Date endDate;

    private Long userId;

    public Budget(double amount, double expenditure, Date startDate, Date endDate, Long userId) {
        this.amount = amount;
        this.expenditure = expenditure;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }
}
