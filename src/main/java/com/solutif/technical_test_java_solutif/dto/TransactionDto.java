package com.solutif.technical_test_java_solutif.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String transactionTime;
    private String accountFrom;
    private String accountTo;
    private BigDecimal amount;
    private String remarks;
    private String reference;
}
