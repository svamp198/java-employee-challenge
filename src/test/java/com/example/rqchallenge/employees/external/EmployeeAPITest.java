package com.example.rqchallenge.employees.external;

import com.example.rqchallenge.employees.exceptions.CustomError;
import com.example.rqchallenge.employees.exceptions.CustomException;
import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.models.EmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeAPITest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeAPI employeeAPI;

    private static final String BASE_URL = "https://dummy.restapiexample.com/api/v1";

    private String allEmployeesJson;

    private final String getEmployeesUrl = BASE_URL + "/employees";

    @BeforeEach
    void setUp() {
        allEmployeesJson = "{ \"status\": \"success\", \"data\": [" +
                "{ \"id\": \"1\", \"employee_name\": \"Virat Kohli\", \"employee_salary\": \"50000\", \"employee_age\": \"30\", \"profile_image\": \"\" }," +
                "{ \"id\": \"2\", \"employee_name\": \"Rohit Sharma\", \"employee_salary\": \"70000\", \"employee_age\": \"40\", \"profile_image\": \"\" }" +
                "] }";
    }

    @Test
    void getAllEmployees_Success() throws JsonProcessingException {
        Employee employee = new Employee(
                "1", "John Doe", "30", "50000"
        );
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(employee));

        when(restTemplate.getForObject(getEmployeesUrl, String.class)).thenReturn(allEmployeesJson);
        when(objectMapper.readValue(allEmployeesJson, EmployeeResponse.class)).thenReturn(employeeResponse);

        List<Employee> employees = employeeAPI.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    void getAllEmployees_RestClientException() {
        when(restTemplate.getForObject(getEmployeesUrl, String.class)).thenThrow(new RestClientException("Error"));

        CustomException exception = assertThrows(CustomException.class, () -> {
            employeeAPI.getAllEmployees();
        });

        assertEquals(CustomError.REST_CLIENT_ERROR, exception.getError());
        assertEquals("Error while making a Rest API call", exception.getMessage());
        assertEquals("E001", exception.getError().getCode());
    }

    @Test
    void getAllEmployees_JsonProcessingException() throws JsonProcessingException {
        when(restTemplate.getForObject(getEmployeesUrl, String.class)).thenReturn(allEmployeesJson);
        when(objectMapper.readValue(allEmployeesJson, EmployeeResponse.class)).thenThrow(new JsonProcessingException("Error") {
        });

        CustomException exception = assertThrows(CustomException.class, () -> {
            employeeAPI.getAllEmployees();
        });

        assertEquals(CustomError.JSON_PROCESSING_ERROR, exception.getError());
        assertEquals("Error while processing JSON", exception.getMessage());
        assertEquals("E002", exception.getError().getCode());
    }
}
