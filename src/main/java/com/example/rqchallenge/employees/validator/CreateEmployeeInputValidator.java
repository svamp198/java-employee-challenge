package com.example.rqchallenge.employees.validator;

import com.example.rqchallenge.employees.exceptions.ValidationException;
import com.example.rqchallenge.employees.models.CreateEmployeeDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.example.rqchallenge.employees.exceptions.CustomError.*;

@Slf4j
public class CreateEmployeeInputValidator {

    public static CreateEmployeeDTO convertAndValidateEmployeeInput(Map<String, Object> employeeInput) throws ValidationException {
        log.info("Starting validation for create employee input:{}", employeeInput);
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO();

        if (!employeeInput.containsKey("name") || !(employeeInput.get("name") instanceof String)) {
            throw new ValidationException(INVALID_OR_MISSING_NAME);
        }
        createEmployeeDTO.setName((String) employeeInput.get("name"));

        if (!employeeInput.containsKey("salary") || !(employeeInput.get("salary") instanceof String)) {
            throw new ValidationException(INVALID_OR_MISSING_SALARY);
        }
        String salaryStr = (String) employeeInput.get("salary");
        if (Integer.parseInt(salaryStr) < 0) {
            throw new ValidationException(NEGATIVE_SALARY_ERROR);
        }
        createEmployeeDTO.setSalary(salaryStr);

        if (!employeeInput.containsKey("age") || !(employeeInput.get("age") instanceof String)) {
            throw new ValidationException(INVALID_OR_MISSING_AGE);
        }
        String ageStr = (String) employeeInput.get("age");
        if (Integer.parseInt(ageStr) < 0) {
            throw new ValidationException(NEGATIVE_AGE_ERROR);
        } else if (Integer.parseInt(ageStr) > 100) {
            throw new ValidationException(AGE_MORE_THAN_100_ERROR);
        }
        createEmployeeDTO.setAge(ageStr);
        log.info("Validation successful for create employee input");

        return createEmployeeDTO;
    }
}

