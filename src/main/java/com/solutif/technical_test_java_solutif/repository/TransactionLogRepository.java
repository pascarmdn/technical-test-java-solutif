package com.solutif.technical_test_java_solutif.repository;

import com.solutif.technical_test_java_solutif.entity.TransactionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionLogRepository  extends JpaRepository<TransactionLog, String> {
    @Query("SELECT t FROM TransactionLog t WHERE t.success = true")
    Page<TransactionLog> findSuccessfulTransactions(Pageable pageable);

    @Query("SELECT SUM(t.amount) FROM TransactionLog t WHERE t.accountFrom = :accountNumber AND t.success = true")
    BigDecimal totalBalance(@Param("accountNumber") String accountNumber);
}
