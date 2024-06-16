package com.solutif.technical_test_java_solutif.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_log")
@Data
@NoArgsConstructor
public class TransactionLog {
    @Id
    @Column(length = 36)
    private String id = UUID.randomUUID().toString();

    private String internalReference;
    private String externalReference;
    private String accountFrom;
    private String accountTo;
    private BigDecimal amount;
    private String remarks;
    private boolean success;

    @CreationTimestamp
    private LocalDate createdDate;

    @Column(nullable = false, updatable = false)
    private String createdBy = "SYSTEM";

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private String modifiedBy = "SYSTEM";

}
