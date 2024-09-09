package com.example.reward.controller;

import com.example.reward.error.InvalidDateException;
import com.example.reward.error.RewardCalculationException;
import com.example.reward.service.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

    @Autowired
    private RewardService rewardService;

    @GetMapping("/{customerId}")
    public Map<Month, Integer> getCustomerRewards(@PathVariable Long customerId,
                                                  @RequestParam("start") String start,
                                                  @RequestParam("end") String end) {
        try {
            // Parsing start and end date
            LocalDateTime startDate = LocalDateTime.parse(start);
            LocalDateTime endDate = LocalDateTime.parse(end);

            logger.info("Fetching rewards for customer ID: {} from {} to {}", customerId, startDate, endDate);

            // Call service to get rewards
            return rewardService.calculateMonthlyRewards(customerId, startDate, endDate);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format for start: {} or end: {} - {}", start, end, e.getMessage());
            throw new InvalidDateException("Invalid date format. Expected format: yyyy-MM-ddTHH:mm:ss", e);
        } catch (Exception e) {
            logger.error("Error occurred while fetching rewards for customer ID: {} - {}", customerId, e.getMessage());
            throw new RewardCalculationException("An error occurred while calculating rewards.", e);
        }
    }
}
