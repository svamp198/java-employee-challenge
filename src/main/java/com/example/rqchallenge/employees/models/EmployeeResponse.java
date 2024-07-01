package com.example.rqchallenge.employees.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeResponse {
    private String status;
    private List<Employee> data;

    public List<Employee> getData() {
        return data;
    }
}
