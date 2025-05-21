package com.example.spring_jooq.exception;

import com.example.spring_jooq.dto.ErrorResponse;
import com.example.spring_jooq.util.ErrorResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateEmployeeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmployee(DuplicateEmployeeException ex, HttpServletRequest request) {
        logger.warn("Duplicate employee: {}", ex.getMessage());
        ErrorResponse error = ErrorResponseUtil.buildError(
                HttpStatus.CONFLICT.value(), "Conflict", "DUPLICATE_EMPLOYEE", ex.getMessage(), request
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex, HttpServletRequest request) {
        logger.info("Employee not found: {}", ex.getMessage());
        ErrorResponse error = ErrorResponseUtil.buildError(
                HttpStatus.NOT_FOUND.value(), "Not Found", "EMPLOYEE_NOT_FOUND", ex.getMessage(), request
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected error", ex);
        ErrorResponse error = ErrorResponseUtil.buildError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "INTERNAL_ERROR", ex.getMessage(), request
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
