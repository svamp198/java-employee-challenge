package com.example.rqchallenge.employees.controllers;

import com.example.rqchallenge.employees.exceptions.CustomError;
import com.example.rqchallenge.employees.exceptions.CustomException;
import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmployeeControllerImplementation.class)
public class EmployeeControllerImplementationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Employee> employees;

    private Employee employee;
    private final String baseUrl = "/api/v1";

    @BeforeEach
    void setUp() {
        employees = Arrays.asList(
                new Employee("1","Virat Kohli","30","50000"),
                new Employee("2","Rohit Sharma","40","70000")
        );
        employee = new Employee("1","Virat Kohli","30","50000");
    }

    @Test
    void getAllEmployees_Success() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        String getAllEmployeesUrl = baseUrl + "/employees";
        mockMvc.perform(get(getAllEmployeesUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));
    }

    @Test
    void getAllEmployees_RestClientError() throws Exception {
        when(employeeService.getAllEmployees()).thenThrow(new CustomException(CustomError.REST_CLIENT_ERROR));

        String getAllEmployeesUrl = baseUrl + "/employees";
        mockMvc.perform(get(getAllEmployeesUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"E001\",\"message\":\"Error while making a Rest API call\"}"));
    }

    @Test
    void getAllEmployees_JsonParsingError() throws Exception {
        when(employeeService.getAllEmployees()).thenThrow(new CustomException(CustomError.JSON_PROCESSING_ERROR));

        String getAllEmployeesUrl = baseUrl + "/employees";
        mockMvc.perform(get(getAllEmployeesUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"E002\",\"message\":\"Error while processing JSON\"}"));
    }

    @Test
    void getEmployeesByNameSearch_Success() throws Exception {
        employees = Arrays.asList(
                new Employee("1","Virat Kohli","30","50000"),
                new Employee("3","Virat Singh","35","55000")
        );
        when(employeeService.getEmployeesByNameSearch("Virat")).thenReturn(employees);
        String employeeName = "Virat";

        String getAllEmployeesUrl = baseUrl + "/employees/search/{searchString}";
        mockMvc.perform(get(getAllEmployeesUrl, employeeName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));
    }

    @Test
    void getHighestSalaryOfEmployees() throws Exception {
        Integer highestSalaryOfEmployees = 70000;
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(highestSalaryOfEmployees);

        String getAllEmployeesUrl = baseUrl + "/employee/highest-salary";
        mockMvc.perform(get(getAllEmployeesUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(highestSalaryOfEmployees)));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_Success() throws Exception {
        List<String> expectedTop10HighestEarningEmployeeNames = List.of(
                "Virat Kohli",
                "Jasprit Bumrah",
                "Hardik Pandya",
                "Ravindra Jadeja",
                "Rohit Sharma",
                "Ravichandran Ashwin",
                "Bhuvneshwar Kumar",
                "KL Rahul",
                "Mohammed Shami",
                "Suryakumar Yadav"
        );
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(expectedTop10HighestEarningEmployeeNames);

        String getAllEmployeesUrl = baseUrl + "/employee/top-10-highest-earning";
        mockMvc.perform(get(getAllEmployeesUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedTop10HighestEarningEmployeeNames)));
    }

    @Test
    void getEmployeeById_Success() throws Exception {
        when(employeeService.getEmployeeById("1")).thenReturn(employee);

        String getEmployeeByIdUrl = baseUrl + "/employee/1";
        mockMvc.perform(get(getEmployeeByIdUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employee)));
    }
}
