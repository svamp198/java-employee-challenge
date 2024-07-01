package com.example.rqchallenge.employees.exceptions;

public enum CustomError {
    REST_CLIENT_ERROR("E001", "Error while making a Rest API call"),
    JSON_PROCESSING_ERROR("E002", "Error while processing JSON"),
    NO_DATA_FOUND("E003", "No Employees found"),
    EMPLOYEE_WITH_GIVEN_NAME_NOT_FOUND("E004", "No Employees present for the given name"),
    GENERAL_ERROR("E999", "An unexpected error occurred");

    private final String code;
    private final String message;

    CustomError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

