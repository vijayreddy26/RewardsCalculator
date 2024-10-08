package com.example.reward.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private Double amount;
    private LocalDateTime transactionDate;

    public Transaction(long l, double l1, LocalDateTime l11) {
       this.customerId = l;
       this.amount = l1;
       this.transactionDate = l11;

   }
}
