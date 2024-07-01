package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
public class CreateEmployee {
    @JsonProperty("id")
    @JsonIgnore
    private Integer id;
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
