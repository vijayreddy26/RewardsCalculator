
package com.example.reward;

import com.example.reward.model.Transaction;
import com.example.reward.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class StartupData implements CommandLineRunner {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {
        Double a = 120.0;
        LocalDateTime l1= LocalDateTime.of(LocalDate.of(2024,1,15), LocalTime.NOON);
        LocalDateTime l2= LocalDateTime.of(LocalDate.of(2024,1,15), LocalTime.NOON);
        transactionRepository.save(new Transaction(1L, 120.0, l1));
        transactionRepository.save(new Transaction(1L, 80, LocalDateTime.of(2024, 2, 15, 10, 0)));
        // Add more sample transactions
    }
}
