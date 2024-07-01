package com.example.rqchallenge.employees.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateEmployeeResponse {
    private String status;
    private CreateEmployee data;
}
