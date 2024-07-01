package com.example.rqchallenge.employees.exceptions;

public enum CustomError {
    REST_CLIENT_ERROR("E001", "Error while making a Rest API call"),
    JSON_PROCESSING_ERROR("E002", "Error while processing JSON"),
    NO_DATA_FOUND("E003", "No Employees found"),
    EMPLOYEE_WITH_GIVEN_NAME_NOT_FOUND("E004", "No Employees present for the given name"),
    INVALID_OR_MISSING_NAME("E005", "Invalid or missing name"),
    INVALID_OR_MISSING_SALARY("E006", "Invalid or missing salary"),
    NEGATIVE_SALARY_ERROR("E007", "Salary cannot be less than zero"),
    INVALID_OR_MISSING_AGE("E008", "Invalid or missing age"),
    NEGATIVE_AGE_ERROR("E009", "Age cannot be less than zero"),
    AGE_MORE_THAN_100_ERROR("E010", "Age cannot be more than hundred"),
    ID_CAN_NOT_BE_NULL("E011", "Id cannot be null"),
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

