package com.example.demo.dto;

import com.example.demo.model.Expense;
import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        String category,
        String description,
        Double amount,
        LocalDate date) {

    public static ExpenseResponse from(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate());
    }
}