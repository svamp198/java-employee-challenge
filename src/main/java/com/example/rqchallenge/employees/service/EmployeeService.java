package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.exceptions.CustomError;
import com.example.rqchallenge.employees.exceptions.CustomException;
import com.example.rqchallenge.employees.exceptions.ValidationException;
import com.example.rqchallenge.employees.external.EmployeeAPI;
import com.example.rqchallenge.employees.models.CreateEmployee;
import com.example.rqchallenge.employees.models.CreateEmployeeDTO;
import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.validator.CreateEmployeeInputValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {
//    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class); //lombok

    private final EmployeeAPI employeeAPI;

    public EmployeeService(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = employeeAPI.getAllEmployees();
        if(allEmployees.size() == 0)
            throw new CustomException(CustomError.NO_DATA_FOUND);
        log.info("List of all Employees is : {}", allEmployees.toString());
        return allEmployees;
    }

    public List<Employee> getEmployeesByNameSearch(String name) {
        List<Employee> employeesFoundByName = getAllEmployees().stream()
                .filter(employee -> employee.getName().contains(name))
                .collect(Collectors.toList());
        if(employeesFoundByName.size()==0)
            throw new CustomException(CustomError.EMPLOYEE_WITH_GIVEN_NAME_NOT_FOUND);
        log.info("Employees with name " + name + " are : {}", employeesFoundByName);
        return employeesFoundByName;
    }

    public int getHighestSalaryOfEmployees() {
        int highestSalaryOfEmployees = getAllEmployees().stream()
                .mapToInt(employee -> Integer.parseInt(employee.getSalary()))
                .max()
                .orElse(0);
        log.info("Highest earning employee salary is: {}", highestSalaryOfEmployees);
        return highestSalaryOfEmployees;
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<String> top10HighestEarningEmployeeNames = getAllEmployees().stream()
                .sorted((e1, e2) -> Integer.compare(Integer.parseInt(e2.getSalary()), Integer.parseInt(e1.getSalary())))
                .limit(10)
                .map(Employee::getName)
                .collect(Collectors.toList());
        log.info("Top 10 highest earning employee names: {}", top10HighestEarningEmployeeNames);
        return top10HighestEarningEmployeeNames;
    }

    public Employee getEmployeeById(String id) {
        if(id == null)
            throw new ValidationException(CustomError.ID_CAN_NOT_BE_NULL);
        Employee employee = employeeAPI.getEmployeeById(id);
        if(employee == null)
            throw new CustomException(CustomError.NO_DATA_FOUND);
        log.info("Successfully found Employee with id : {}", employee);
        return employee;
    }

    public CreateEmployee createEmployee(Map<String, Object> employeeInput) {
        CreateEmployeeDTO createEmployeeDTO = CreateEmployeeInputValidator.convertAndValidateEmployeeInput(employeeInput);
        CreateEmployee createEmployee = employeeAPI.createEmployee(createEmployeeDTO);
        log.info("Successfully created Employee with id : {}", createEmployee);
        return createEmployee;
    }

    public String deleteEmployee(String id) {
        if(id == null)
            throw new ValidationException(CustomError.ID_CAN_NOT_BE_NULL);
        Employee employeeToBeDeleted = getEmployeeById(id);
        employeeAPI.deleteEmployee(employeeToBeDeleted);
        return employeeToBeDeleted.getName();
    }
}
