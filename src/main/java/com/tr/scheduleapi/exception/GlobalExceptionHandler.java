package com.tr.scheduleapi.exception; // 예외 로직 분기

// Error Code
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
// 분기 + 정리
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Map, 예외
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice // 횡단
public class GlobalExceptionHandler {

    // DTO @Valid -> 검증 실패 -> MethodArgumentNotValidException
    // 예외를 가로채서(handle) 각 필드 오류를 field : message로 수집
    // HTTP 400과 함께, 표준화된 JSON Body로 리턴.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", "validation_failed",
                "details", errors
        ));
    }

    // 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", "illegal_argument",
                "details", ex.getMessage()
        ));
    }

    // 404
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "resource_not_found",
                "message", ex.getMessage()
        ));
    }

    // 403
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePassword(PasswordMismatchException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                "error", "password_mismatch",
                "message", ex.getMessage()
        ));
    }

    // 500으로 틀어막기
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOthers(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "internal_error",
                "message", ex.getMessage()
        ));
    }
}
// 보충 필요
