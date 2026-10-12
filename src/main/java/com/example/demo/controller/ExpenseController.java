package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.Expense;
import com.example.demo.dto.ExpenseRequest;
import com.example.demo.dto.ExpenseResponse;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName())
            .orElseThrow(() -> new IllegalStateException("Authenticated user was not found"));
    }

    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses() {
        return expenseRepository.findByUser(getCurrentUser()).stream()
                .map(ExpenseResponse::from)
                .toList();
    }

    @PostMapping("/expenses")
    public ExpenseResponse addExpense(@RequestBody ExpenseRequest request) {
        Expense expense = new Expense();
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setDate(request.getDate());
        expense.setUser(getCurrentUser());
        return ExpenseResponse.from(expenseRepository.save(expense));
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        long deleted = expenseRepository.deleteByIdAndUser(id, getCurrentUser());
        return deleted == 0 ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }

}
