package com.example.demo.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ErrorResponseDto {

    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponseDto(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
}