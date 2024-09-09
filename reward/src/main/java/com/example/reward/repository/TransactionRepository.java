// File: src/main/java/com/example/rewards/repository/TransactionRepository.java
package com.example.reward.repository;

import com.example.reward.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerIdAndTransactionDateBetween(Long customerId, LocalDateTime start, LocalDateTime end);
}
