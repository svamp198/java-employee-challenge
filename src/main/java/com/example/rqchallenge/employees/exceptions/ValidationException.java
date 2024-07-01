package com.example.rqchallenge.employees.exceptions;

public class ValidationException  extends RuntimeException {
    private final CustomError error;

    public ValidationException(CustomError error) {
        this.error = error;
    }

    public CustomError getError() {
        return error;
    }
}


