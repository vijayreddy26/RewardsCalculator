package com.example.reward.service;

import com.example.reward.model.Transaction;
import com.example.reward.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<Month, Integer> calculateMonthlyRewards(Long customerId, LocalDateTime start, LocalDateTime end) {
        Map<Month, Integer> rewardsPerMonth = new HashMap<>();
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, start, end);

        for (Transaction transaction : transactions) {
            int points = calculatePoints(transaction.getAmount());
            if (points > 0) { // Only add if points > 0
                Month month = transaction.getTransactionDate().getMonth();
                rewardsPerMonth.put(month, rewardsPerMonth.getOrDefault(month, 0) + points);
            }
        }

        return rewardsPerMonth;
    }

    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;
            amount = 100;
        }
        if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }
}
