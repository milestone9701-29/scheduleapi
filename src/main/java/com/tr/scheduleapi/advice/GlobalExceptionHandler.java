package com.tr.scheduleapi.advice;

import com.tr.scheduleapi.exception.CommentLimitExceededException;
import com.tr.scheduleapi.exception.PasswordMismatchException;
import com.tr.scheduleapi.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> body(HttpStatus status, String message, String path) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("timestamp", OffsetDateTime.now().toString()); // now() 호출
        m.put("status", status.value());
        m.put("error", status.getReasonPhrase());
        m.put("message", message);
        m.put("path", path);
        return m;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, Object> m = body(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI());
        List<Map<String, Object>> errors = ex.getBindingResult().getFieldErrors().stream().map(fe -> {
            Map<String, Object> e = new LinkedHashMap<>();
            e.put("field", fe.getField());
            e.put("rejectedValue", fe.getRejectedValue());
            e.put("message", fe.getDefaultMessage());
            return e;
        }).toList(); // Java 17 OK
        m.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePassword(PasswordMismatchException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(body(HttpStatus.FORBIDDEN, ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(CommentLimitExceededException.class)
    public ResponseEntity<?> handleLimit(CommentLimitExceededException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(body(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI()));

        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleAny(Exception ex, HttpServletRequest req){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(body(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", req.getRequestURI()));
        }

    }
}
