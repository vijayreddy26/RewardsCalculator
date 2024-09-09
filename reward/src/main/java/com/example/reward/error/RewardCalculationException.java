package com.example.reward.error;
public class RewardCalculationException extends RuntimeException {
    public RewardCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}