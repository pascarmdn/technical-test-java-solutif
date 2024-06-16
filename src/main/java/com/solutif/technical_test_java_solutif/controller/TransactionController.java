package com.solutif.technical_test_java_solutif.controller;

import com.solutif.technical_test_java_solutif.exception.GlobalExceptionHandler;
import com.solutif.technical_test_java_solutif.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/transaction")
    public Map<String, Object> getTransactions(@RequestParam(defaultValue = "1") int page) {
        if (page < 1) {
            throw new GlobalExceptionHandler.InvalidParameterException("Page number must be greater than 0");
        }
        return service.getSuccessfulTransactions(page - 1, 20);
    }

    @GetMapping("/summary/{accountNumber}")
    public Map<String, Object> getSummary(@PathVariable String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new GlobalExceptionHandler.InvalidParameterException("Account number must not be empty");
        }
        return service.getTotalBalance(accountNumber);
    }
}
