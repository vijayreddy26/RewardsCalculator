package com.example.reward.controller;

import com.example.reward.error.InvalidDateException;
import com.example.reward.error.RewardCalculationException;
import com.example.reward.service.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/calculate")
    public Map<Month, Integer> calculateRewards(
            @RequestParam Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return rewardService.calculateMonthlyRewards(customerId, start, end);
    }


    }

