package com.example.demo.service;

import com.example.demo.model.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repo;

    public List<Expense> listAll() {
        return repo.findAll();
    }

    public void save(Expense expense) {
        repo.save(expense);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Expense get(Long id) {
        return repo.findById(id).orElse(null);
    }
}
