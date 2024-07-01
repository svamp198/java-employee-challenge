package com.example.rqchallenge.employees.controllers;


import com.example.rqchallenge.employees.models.CreateEmployee;
import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class EmployeeControllerImplementation implements IEmployeeController {
    private final EmployeeService employeeService;

    public EmployeeControllerImplementation(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("calling api to get all employees ");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/employees/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        log.info("calling api to get all employees with name " + searchString);
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @GetMapping("/employee/highest-salary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.info("calling api to get highest salaries of employees");
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @Override
    @GetMapping("/employee/top-10-highest-earning")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.info("calling api to get top 10 highest earning employees");
        return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CreateEmployee> createEmployee(Map<String, Object> employeeInput) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeInput));
    }

    @Override
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
