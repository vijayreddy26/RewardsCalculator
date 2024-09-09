package com.example.reward.error;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}