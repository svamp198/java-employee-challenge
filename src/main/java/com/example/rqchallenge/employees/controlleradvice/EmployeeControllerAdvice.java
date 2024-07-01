package com.example.rqchallenge.employees.controlleradvice;

import com.example.rqchallenge.employees.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class EmployeeControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerAdvice.class); //lombok

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException ex) {
        logger.info("handling CustomException because " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getError().getCode());
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        logger.info("handling Exception because " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "GENERAL_ERROR");
        errorResponse.put("message", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
