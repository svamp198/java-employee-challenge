package com.example.rqchallenge.employees.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.example.rqchallenge.employees.exceptions.ValidationException;
import com.example.rqchallenge.employees.models.CreateEmployeeDTO;
import org.junit.jupiter.api.Test;
        import static org.junit.jupiter.api.Assertions.*;

        import java.util.HashMap;
        import java.util.Map;

class CreateEmployeeInputValidatorTest {

    @Test
    void convertAndValidateEmployeeInput_Success() throws ValidationException {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "50000");
        employeeInput.put("age", "30");

        CreateEmployeeDTO result = CreateEmployeeInputValidator.convertAndValidateEmployeeInput(employeeInput);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("50000", result.getSalary());
        assertEquals("30", result.getAge());
    }

    @Test
    void convertAndValidateEmployeeInput_MissingName() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("salary", "50000");
        employeeInput.put("age", "30");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            CreateEmployeeInputValidator.convertAndValidateEmployeeInput(employeeInput);
        });

        assertEquals("Invalid or missing name", exception.getError().getMessage());
        assertEquals("E005", exception.getError().getCode());
    }

    @Test
    void convertAndValidateEmployeeInput_InvalidSalaryType() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", 50000);
        employeeInput.put("age", "30");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            CreateEmployeeInputValidator.convertAndValidateEmployeeInput(employeeInput);
        });

        assertEquals("Invalid or missing salary", exception.getError().getMessage());
        assertEquals("E006", exception.getError().getCode());
    }

    @Test
    void convertAndValidateEmployeeInput_InvalidAgeType() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "50000");
        employeeInput.put("age", 30);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            CreateEmployeeInputValidator.convertAndValidateEmployeeInput(employeeInput);
        });

        assertEquals("Invalid or missing age", exception.getError().getMessage());
        assertEquals("E008", exception.getError().getCode());
    }

    @Test
    void convertAndValidateEmployeeInput_NegativeSalary() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "-50000");
        employeeInput.put("age", "30");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            CreateEmployeeInputValidator.convertAndValidateEmployeeInput(employeeInput);
        });

        assertEquals("Salary cannot be less than zero", exception.getError().getMessage());
        assertEquals("E007", exception.getError().getCode());
    }

    @Test
    void convertAndValidateEmployeeInput_NegativeAge() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "50000");
        employeeInput.put("age", "-30");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            CreateEmployeeInputValidator.convertAndValidateEmployeeInput(employeeInput);
        });

        assertEquals("Age cannot be less than zero", exception.getError().getMessage());
        assertEquals("E009", exception.getError().getCode());
    }

    @Test
    void convertAndValidateEmployeeInput_AgeMoreThan100() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "50000");
        employeeInput.put("age", "130");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            CreateEmployeeInputValidator.convertAndValidateEmployeeInput(employeeInput);
        });

        assertEquals("Age cannot be more than hundred", exception.getError().getMessage());
        assertEquals("E010", exception.getError().getCode());
    }
}
