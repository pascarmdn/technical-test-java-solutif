package com.solutif.technical_test_java_solutif.service;

import com.solutif.technical_test_java_solutif.dto.TransactionDto;
import com.solutif.technical_test_java_solutif.entity.TransactionLog;
import com.solutif.technical_test_java_solutif.exception.GlobalExceptionHandler;
import com.solutif.technical_test_java_solutif.repository.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionLogRepository repository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    @Autowired
    public TransactionService(TransactionLogRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> getSuccessfulTransactions(int page, int size) {
        Page<TransactionLog> transactionsPage = repository.findSuccessfulTransactions(PageRequest.of(page, size));
        if (transactionsPage.isEmpty()) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("No successful transactions found.");
        }
        List<TransactionLog> transactions = transactionsPage.getContent();

        List<TransactionDto> mappingData = transactions.stream().map(this::mapToDto).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> metadata = new HashMap<>();

        metadata.put("request_time", LocalDateTime.now().format(formatter));
        metadata.put("currentPage", transactionsPage.getNumber() + 1);
        metadata.put("dataPage", transactionsPage.getSize());
        metadata.put("totalPage",transactionsPage.getTotalPages());
        metadata.put("totalData", transactionsPage.getTotalElements());
        response.put("data", mappingData);
        response.put("metadata", metadata);

        return response;
    }

    private TransactionDto mapToDto(TransactionLog transactionLog) {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionTime(transactionLog.getModifiedDate().format(formatter));
        dto.setAccountFrom(transactionLog.getAccountFrom());
        dto.setAccountTo(transactionLog.getAccountTo());
        dto.setAmount(transactionLog.getAmount());
        dto.setRemarks(transactionLog.getRemarks());
        dto.setReference(transactionLog.getExternalReference());

        return dto;
    }

    public Map<String, Object> getTotalBalance(String accountNumber) {
        BigDecimal totalBalance = repository.totalBalance(accountNumber);
        if (totalBalance == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account number " + accountNumber + " not found or no successful transactions.");
        }

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        data.put("request_time", LocalDateTime.now().format(formatter));
        data.put("account_number", accountNumber);
        data.put("total_balance", totalBalance);
        response.put("data", data);

        return response;
    }
}
