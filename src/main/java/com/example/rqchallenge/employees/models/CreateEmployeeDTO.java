package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Map;

@Data
@NoArgsConstructor
public class CreateEmployeeDTO {

    @JsonProperty("name")
    @JsonIgnore
    private String name;
    @JsonProperty("salary")
    @JsonIgnore
    private String salary;
    @JsonProperty("age")
    @JsonIgnore
    private String age;
}


