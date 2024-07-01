package com.example.rqchallenge.employees.external;

import com.example.rqchallenge.employees.exceptions.CustomError;
import com.example.rqchallenge.employees.exceptions.CustomException;
import com.example.rqchallenge.employees.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class EmployeeAPI {
    private static final String BASE_URL = "https://dummy.restapiexample.com/api/v1";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EmployeeAPI(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public List<Employee> getAllEmployees() {
        try {
            String externalUrl =  BASE_URL + "/employees";
            log.info("calling external api at to get all employees "  + externalUrl );
            String response = restTemplate.getForObject(externalUrl, String.class);
            log.info("Successfully fetched response from api: {}", response);

            EmployeeResponse employeeResponse = objectMapper.readValue(response, EmployeeResponse.class);
            log.info("Successfully parsed response: {}", employeeResponse);

            return employeeResponse.getData();
        }
        catch (RestClientException e) {
            log.error("Error while fetching all employees " + e.getMessage(), e);
            throw new CustomException(CustomError.REST_CLIENT_ERROR);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing employees response " + e.getMessage(), e);
            throw new CustomException(CustomError.JSON_PROCESSING_ERROR);
        }
    }

    public Employee getEmployeeById(String id) {
        try {
            String externalUrl =  BASE_URL + "/employee/" + id;
            log.info("calling external api at to get all employees "  + externalUrl );
            String response = restTemplate.getForObject(externalUrl, String.class);
            log.info("Successfully fetched response from api: {}", response);

            EmployeeResponse employeeResponse = objectMapper.readValue(response, EmployeeResponse.class);
            log.info("Successfully parsed response: {}", employeeResponse);

            return employeeResponse.getData().get(0);
        } catch (RestClientException e) {
            log.error("Error while fetching all employees " + e.getMessage(), e);
            throw new CustomException(CustomError.REST_CLIENT_ERROR);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing employees response " + e.getMessage(), e);
            throw new CustomException(CustomError.JSON_PROCESSING_ERROR);
        }
    }

    public CreateEmployee createEmployee(CreateEmployeeDTO createEmployeeDTO) {
        try {
            String externalUrl =  BASE_URL + "/create";
            log.info("calling external api at to get all employees "  + externalUrl );
            String response = restTemplate.postForObject(externalUrl, createEmployeeDTO, String.class);
            log.info("Successfully fetched response from api: {}", response);

            CreateEmployeeResponse createEmployeeResponse = objectMapper.readValue(response, CreateEmployeeResponse.class);
            log.info("Successfully parsed response: {}", createEmployeeResponse);

            return createEmployeeResponse.getData();
        } catch (RestClientException e) {
            log.error("Error while fetching all employees " + e.getMessage(), e);
            throw new CustomException(CustomError.REST_CLIENT_ERROR);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing employees response " + e.getMessage(), e);
            throw new CustomException(CustomError.JSON_PROCESSING_ERROR);
        }
    }
}
