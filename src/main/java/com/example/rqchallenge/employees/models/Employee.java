package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    @JsonProperty("id")
    private String id;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_age")
    private String age;
    @JsonProperty("employee_salary")
    private String salary;
}
