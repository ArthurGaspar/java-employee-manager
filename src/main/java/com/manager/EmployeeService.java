package com.manager;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private List<Employee> employees;
    private FileDataService fileDataService;

    public EmployeeService() {
        this.fileDataService = new FileDataService();
        this.employees = fileDataService.loadEmployees();
    }

    // OPTION 1 - View employees by ID
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
    
    // OPTION 2 - Add new employee
    public void getAddEmployee(Employee employee) {
        employees.add(employee);
        fileDataService.saveEmployees(employees);
    }
    
    // OPTION 3 - View employees by department
    public List<String> getAllDepartments() {
        return employees.stream()
            .map(Employee::getDepartment)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employees.stream()
            .filter(e -> e.getDepartment().equalsIgnoreCase(department))
            .collect(Collectors.toList());
    }
    
    // OPTION 4 - Find highest paid employee
    public Optional<Employee> getHighestPaidEmployee() {
        return employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
    }
    
    // OPTION 5 - Calculate average salary
    public double getAverageSalary() {
        return employees.stream()
            .mapToDouble(Employee::getSalary)
            .average()
            .orElse(0.0);
    }
    
    // OPTION 6 - View employees alphabetically
    public List<Employee> getEmployeesSortedByName() {
        return employees.stream()
            .sorted(Comparator.comparing(Employee::getName))
            .collect(Collectors.toList());
    }
}