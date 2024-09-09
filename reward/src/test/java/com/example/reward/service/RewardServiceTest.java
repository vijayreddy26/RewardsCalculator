package com.example.reward.service;

import com.example.reward.model.Transaction;
import com.example.reward.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateMonthlyRewards_NoTransactions() {
        Long customerId = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, start, end))
                .thenReturn(Collections.emptyList());

        Map<Month, Integer> rewards = rewardService.calculateMonthlyRewards(customerId, start, end);

        assertEquals(0, rewards.size());
    }

    @Test
    public void testCalculateMonthlyRewards_SingleTransaction() {
        Long customerId = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        Transaction transaction = new Transaction(1L, customerId, 120.0, LocalDateTime.of(2023, 6, 15, 10, 0));
        
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, start, end))
                .thenReturn(Collections.singletonList(transaction));

        Map<Month, Integer> rewards = rewardService.calculateMonthlyRewards(customerId, start, end);

        assertEquals(1, rewards.size());
        assertEquals(90, rewards.get(Month.JUNE)); // 120 -> 40 + 50 = 90 points
    }

    @Test
    public void testCalculateMonthlyRewards_MultipleTransactions() {
        Long customerId = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        
        Transaction transaction1 = new Transaction(1L, customerId, 120.0, LocalDateTime.of(2023, 6, 15, 10, 0));
        Transaction transaction2 = new Transaction(2L, customerId, 70.0, LocalDateTime.of(2023, 6, 20, 10, 0));
        Transaction transaction3 = new Transaction(3L, customerId, 200.0, LocalDateTime.of(2023, 7, 10, 10, 0));
        
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, start, end))
                .thenReturn(transactions);

        Map<Month, Integer> rewards = rewardService.calculateMonthlyRewards(customerId, start, end);

        assertEquals(2, rewards.size());
        assertEquals(110, rewards.get(Month.JUNE)); // 90 + 20 = 110 points
        assertEquals(250, rewards.get(Month.JULY)); // 250 points
    }

    @Test
    public void testCalculateMonthlyRewards_TransactionsWithZeroPoints() {
        Long customerId = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        
        Transaction transaction = new Transaction(1L, customerId, 40.0, LocalDateTime.of(2023, 6, 15, 10, 0));
        
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, start, end))
                .thenReturn(Collections.singletonList(transaction));

        Map<Month, Integer> rewards = rewardService.calculateMonthlyRewards(customerId, start, end);

        assertEquals(1, rewards.size());
        assertEquals(0, rewards.get(Month.JUNE)); // 40 points -> 0 rewards
    }

    @Test
    public void testCalculateMonthlyRewards_TransactionsOutsideDateRange() {
        Long customerId = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);
        
        Transaction transaction = new Transaction(1L, customerId, 120.0, LocalDateTime.of(2022, 12, 15, 10, 0));
        
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, start, end))
                .thenReturn(Collections.singletonList(transaction));

        Map<Month, Integer> rewards = rewardService.calculateMonthlyRewards(customerId, start, end);

        assertEquals(0, rewards.size());
    }
}



