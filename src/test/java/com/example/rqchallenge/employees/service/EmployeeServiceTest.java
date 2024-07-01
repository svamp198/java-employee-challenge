package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.exceptions.CustomException;
import com.example.rqchallenge.employees.external.EmployeeAPI;
import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.models.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeAPI employeeAPI;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void getAllEmployees_Success() {
        Employee virat = new Employee("1","Virat Kohli","30","50000");
        Employee rohit = new Employee("2","Rohit Sharma","40","70000");
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(virat, rohit));
        when(employeeAPI.getAllEmployees()).thenReturn(employeeResponse.getData());

        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals(virat, employees.get(0));
        assertEquals(rohit, employees.get(1));
    }

    @Test
    void getAllEmployees_NoDataFound() {
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of());
        when(employeeAPI.getAllEmployees()).thenReturn(employeeResponse.getData());

        CustomException exception = assertThrows(CustomException.class, () -> {
            employeeService.getAllEmployees();
        });
        assertEquals("No Employees found", exception.getMessage());
        assertEquals("E003", exception.getError().getCode());
    }

    @Test
    void getEmployeesByNameSearch() {
        Employee virat = new Employee("1","Virat Kohli","30","50000");
        Employee rohit = new Employee("2","Rohit Sharma","40","70000");
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(virat, rohit));
        when(employeeAPI.getAllEmployees()).thenReturn(employeeResponse.getData());

        List<Employee> employeesWithNameVirat = employeeService.getEmployeesByNameSearch("Virat");

        assertEquals(1, employeesWithNameVirat.size());
        assertEquals(virat, employeesWithNameVirat.get(0));
    }

    @Test
    void getEmployeesByNameSearch_multipleEmployeesWithSameName() {
        Employee virat = new Employee("1","Virat Kohli","30","50000");
        Employee rohit = new Employee("2","Rohit Sharma","40","70000");
        Employee viratSingh = new Employee("3","Virat Singh","35","55000");
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(virat, rohit, viratSingh));
        when(employeeAPI.getAllEmployees()).thenReturn(employeeResponse.getData());

        List<Employee> employeesWithNameVirat = employeeService.getEmployeesByNameSearch("Virat");

        assertEquals(2, employeesWithNameVirat.size());
        assertEquals(virat, employeesWithNameVirat.get(0));
        assertEquals(viratSingh, employeesWithNameVirat.get(1));
    }

    @Test
    void getEmployeesByNameSearch_NoMatchFound() {
        Employee virat = new Employee("1","Virat Kohli","30","50000");
        Employee rohit = new Employee("2","Rohit Sharma","40","70000");
        Employee viratSingh = new Employee("3","Virat Singh","35","55000");
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(virat, rohit, viratSingh));
        when(employeeAPI.getAllEmployees()).thenReturn(employeeResponse.getData());

        CustomException exception = assertThrows(CustomException.class,() -> {
            employeeService.getEmployeesByNameSearch("Raina");
        });
        assertEquals("No Employees present for the given name", exception.getMessage());
        assertEquals("E004", exception.getError().getCode());
    }

    @Test
    void getHighestSalaryOfEmployees() {
        Employee virat = new Employee("1","Virat Kohli","30","50000");
        Employee rohit = new Employee("2","Rohit Sharma","40","70000");
        Employee jadeja = new Employee("3","Ravindra Jadeja","35","55000");
        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(virat, rohit, jadeja));
        when(employeeAPI.getAllEmployees()).thenReturn(employeeResponse.getData());
        Integer expectedHighestSalaryOfEmployees = 70000;

        Integer highestSalaryOfEmployees = employeeService.getHighestSalaryOfEmployees();

        assertEquals(expectedHighestSalaryOfEmployees, highestSalaryOfEmployees);
    }

    @Test
    void getTop10HighestEarningEmployeeNames() {
        Employee virat = new Employee("1", "Virat Kohli", "34", "60000");
        Employee rohit = new Employee("2", "Rohit Sharma", "36", "55000");
        Employee klRahul = new Employee("3", "KL Rahul", "31", "52000");
        Employee suryakumar = new Employee("4", "Suryakumar Yadav", "33", "51000");
        Employee rishabh = new Employee("5", "Rishabh Pant", "26", "50000");
        Employee hardik = new Employee("6", "Hardik Pandya", "30", "58000");
        Employee jadeja = new Employee("7", "Ravindra Jadeja", "35", "57000");
        Employee ashwin = new Employee("8", "Ravichandran Ashwin", "37", "54000");
        Employee bhuvneshwar = new Employee("9", "Bhuvneshwar Kumar", "34", "53000");
        Employee bumrah = new Employee("10", "Jasprit Bumrah", "30", "60000");
        Employee chahal = new Employee("11", "Yuzvendra Chahal", "34", "51000");
        Employee shami = new Employee("12", "Mohammed Shami", "34", "52000");
        Employee shardul = new Employee("13", "Shardul Thakur", "32", "50000");
        Employee sanju = new Employee("14", "Sanju Samson", "29", "49000");
        Employee ishan = new Employee("15", "Ishan Kishan", "26", "48000");

        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(virat, rohit, klRahul, suryakumar, rishabh, hardik, jadeja, ashwin, bhuvneshwar,
        bumrah, chahal, shami, shardul, sanju, ishan));
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
        when(employeeAPI.getAllEmployees()).thenReturn(employeeResponse.getData());

        List<String> top10HighestEarningEmployeeNames = employeeService.getTopTenHighestEarningEmployeeNames();

        assertEquals(10, top10HighestEarningEmployeeNames.size());
        assertEquals(expectedTop10HighestEarningEmployeeNames, top10HighestEarningEmployeeNames);
    }

    @Test
    void getTop10HighestEarningEmployeeNames_LessThan10Employees() {
        Employee virat = new Employee("1", "Virat Kohli", "34", "60000");
        Employee rohit = new Employee("2", "Rohit Sharma", "36", "55000");
        Employee klRahul = new Employee("3", "KL Rahul", "31", "52000");
        Employee suryakumar = new Employee("4", "Suryakumar Yadav", "33", "51000");
        Employee rishabh = new Employee("5", "Rishabh Pant", "26", "50000");
        Employee hardik = new Employee("6", "Hardik Pandya", "30", "58000");

        EmployeeResponse employeeResponse = new EmployeeResponse("success", List.of(virat, rohit, klRahul, suryakumar, rishabh, hardik));
        List<String> expectedTop10HighestEarningEmployeeNames = List.of(
                "Virat Kohli",
                "Hardik Pandya",
                "Rohit Sharma",
                "KL Rahul",
                "Suryakumar Yadav",
                "Rishabh Pant"
        );
        when(employeeAPI.getAllEmployees()).thenReturn(employeeResponse.getData());

        List<String> top10HighestEarningEmployeeNames = employeeService.getTopTenHighestEarningEmployeeNames();

        assertEquals(6, top10HighestEarningEmployeeNames.size());
        assertEquals(expectedTop10HighestEarningEmployeeNames, top10HighestEarningEmployeeNames);
    }
}
