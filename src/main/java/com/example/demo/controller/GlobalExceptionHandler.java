package com.example.demo.controller;

import com.example.demo.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponseDto responseDto = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "입력값 검증에 실패했습니다.",
                errors
        );
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 내부 오류가 발생했습니다."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }
}