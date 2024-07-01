package com.example.rqchallenge.employees.external;

import com.example.rqchallenge.employees.exceptions.CustomError;
import com.example.rqchallenge.employees.exceptions.CustomException;
import com.example.rqchallenge.employees.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.eq;
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
    private String employeeJson;
    private String createEmployeeJson;

    private final String getEmployeesUrl = BASE_URL + "/employees";
    private final String getEmployeeByIdUrl = BASE_URL + "/employee";
    private final String createEmployeeUrl = BASE_URL + "/create";

    @BeforeEach
    void setUp() {
        allEmployeesJson = "{ \"status\": \"success\", \"data\": [" +
                "{ \"id\": \"1\", \"employee_name\": \"Virat Kohli\", \"employee_salary\": \"50000\", \"employee_age\": \"30\", \"profile_image\": \"\" }," +
                "{ \"id\": \"2\", \"employee_name\": \"Rohit Sharma\", \"employee_salary\": \"70000\", \"employee_age\": \"40\", \"profile_image\": \"\" }" +
                "] }";

        employeeJson = "{ \"status\": \"success\", \"data\": { \"id\": \"1\", \"employee_name\": \"Virat Kohli\", \"employee_salary\": \"50000\", \"employee_age\": \"30\", \"profile_image\": \"\" } }";
        createEmployeeJson = "{ \"status\": \"success\", \"data\": { \"id\": \"1\", \"employee_name\": \"Virat Kohli\", \"employee_salary\": \"50000\", \"employee_age\": \"30\", \"profile_image\": \"\" } }";
    }

    @Test
    void getAllEmployees_Success() throws JsonProcessingException {
        Employee employee = new Employee(
                "1", "Virat Kohli", "30", "50000"
        );
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(employee));

        when(restTemplate.getForObject(getEmployeesUrl, String.class)).thenReturn(allEmployeesJson);
        when(objectMapper.readValue(allEmployeesJson, EmployeeResponse.class)).thenReturn(employeeResponse);

        List<Employee> employees = employeeAPI.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("Virat Kohli", employees.get(0).getName());
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

    @Test
    void getEmployeeById_Success() throws JsonProcessingException {
        Employee employee = new Employee(
                "1", "Virat Kohli", "30", "50000"
        );
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(employee));

        when(restTemplate.getForObject(getEmployeeByIdUrl + "/1", String.class)).thenReturn(employeeJson);
        when(objectMapper.readValue(employeeJson, EmployeeResponse.class)).thenReturn(employeeResponse);

        Employee employeeById = employeeAPI.getEmployeeById("1");

        assertNotNull(employeeById);
        assertEquals(employee, employeeById);
    }

    @Test
    void getEmployeeById_RestClientException() {
        when(restTemplate.getForObject(getEmployeeByIdUrl + "/1", String.class)).thenThrow(new RestClientException("Error while fetching record"));

        CustomException exception = assertThrows(CustomException.class, () -> {
            employeeAPI.getEmployeeById("1");
        });

        assertEquals(CustomError.REST_CLIENT_ERROR, exception.getError());
        assertEquals("Error while making a Rest API call", exception.getMessage());
        assertEquals("E001", exception.getError().getCode());
    }

    @Test
    void getEmployeeById_JsonProcessingException() throws JsonProcessingException {
        when(restTemplate.getForObject(getEmployeeByIdUrl + "/1", String.class)).thenReturn(employeeJson);
        when(objectMapper.readValue(employeeJson, EmployeeResponse.class)).thenThrow(new JsonProcessingException("Error"){});

        CustomException exception = assertThrows(CustomException.class, () -> {
            employeeAPI.getEmployeeById("1");
        });

        assertEquals(CustomError.JSON_PROCESSING_ERROR, exception.getError());
        assertEquals("Error while processing JSON", exception.getMessage());
        assertEquals("E002", exception.getError().getCode());
    }

    @Test
    void createEmployee_Success() throws JsonProcessingException {
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO();
        createEmployeeDTO.setName("Virat Kohli");
        createEmployeeDTO.setSalary("50000");
        createEmployeeDTO.setAge("30");
        String jsonResponse = "{\"status\":\"success\",\"data\":{\"name\":\"Virat Kohli\",\"salary\":\"50000\",\"age\":\"30\"}}";
        CreateEmployee createEmployee = new CreateEmployee(1,"Virat Kohli", "50000", "30");
        CreateEmployeeResponse createEmployeeResponse = new CreateEmployeeResponse("success",createEmployee);

        when(restTemplate.postForObject(createEmployeeUrl,createEmployeeDTO, String.class)).thenReturn(jsonResponse);
        when(objectMapper.readValue(jsonResponse,CreateEmployeeResponse.class)).thenReturn(createEmployeeResponse);

        CreateEmployee result = employeeAPI.createEmployee(createEmployeeDTO);

        assertNotNull(result);
        assertEquals("Virat Kohli", result.getName());
        assertEquals("50000", result.getSalary());
        assertEquals("30", result.getAge());
    }

    @Test
    void createEmployee_RestClientException() {
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO();
        createEmployeeDTO.setName("Virat Kohli");
        createEmployeeDTO.setSalary("50000");
        createEmployeeDTO.setAge("30");

        when(restTemplate.postForObject(createEmployeeUrl,createEmployeeDTO, String.class)).thenThrow(new RestClientException("Error"));

        CustomException exception = assertThrows(CustomException.class, () -> {
            employeeAPI.createEmployee(createEmployeeDTO);
        });

        assertEquals(CustomError.REST_CLIENT_ERROR, exception.getError());
        assertEquals("Error while making a Rest API call", exception.getMessage());
        assertEquals("E001", exception.getError().getCode());
    }

    @Test
    void createEmployee_JsonParsingException() throws JsonProcessingException {
        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO();
        createEmployeeDTO.setName("Virat Kohli");
        createEmployeeDTO.setSalary("50000");
        createEmployeeDTO.setAge("30");
        String jsonResponse = "{\"status\":\"success\",\"data\":{\"name\":\"Virat Kohli\",\"salary\":\"50000\",\"age\":\"30\"}}";

        when(restTemplate.postForObject(createEmployeeUrl,createEmployeeDTO, String.class)).thenReturn(jsonResponse);
        when(objectMapper.readValue(jsonResponse,CreateEmployeeResponse.class)).thenThrow(new JsonProcessingException("Error"){});

        CustomException exception = assertThrows(CustomException.class, () -> {
            employeeAPI.createEmployee(createEmployeeDTO);
        });

        assertEquals(CustomError.JSON_PROCESSING_ERROR, exception.getError());
        assertEquals("Error while processing JSON", exception.getMessage());
        assertEquals("E002", exception.getError().getCode());
    }

    @Test
    void deleteEmployee_Success() {
        Employee employee = new Employee("1", "Virat Kohli", "30", "50000");
        String jsonResponse = "successfully! deleted Record";

        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), isNull(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(jsonResponse));

        String result = employeeAPI.deleteEmployee(employee);

        assertEquals("Virat Kohli", result);
    }

    @Test
    void deleteEmployee_FailureResponse() {
        Employee employee = new Employee("1", "Virat Kohli", "30", "50000");
        String jsonResponse = "failed to delete Record";

        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), isNull(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(jsonResponse));

        String result = employeeAPI.deleteEmployee(employee);

        assertEquals("failed", result);
    }

    @Test
    void deleteEmployee_RestClientException() {
        Employee employee = new Employee("1", "Virat Kohli", "30", "50000");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), isNull(), eq(String.class)))
                .thenThrow(new RestClientException("Error"));

        CustomException exception = assertThrows(CustomException.class, () -> {
            employeeAPI.deleteEmployee(employee);
        });

        assertEquals(CustomError.REST_CLIENT_ERROR.getMessage(), exception.getMessage());
    }
}
