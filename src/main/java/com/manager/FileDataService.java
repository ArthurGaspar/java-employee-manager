package com.manager;

import java.io.*;
import java.util.*;

public class FileDataService {
    private static final String DATA_FILE = "data/employees.csv";
    
    public List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Employee employee = parseEmployee(line);
                    if (employee != null) {
                        employees.add(employee);
                    }
                }
            }
            System.out.println("Loaded " + employees.size() + " employees from file.");
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Starting with empty employee list.");
        } catch (IOException e) {
            System.out.println("Error reading data file: " + e.getMessage());
        }
        
        return employees;
    }
    
    public void saveEmployees(List<Employee> employees) {
        new File("data").mkdirs();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Employee employee : employees) {
                writer.write(employeeToCsv(employee));
                writer.newLine();
            }
            System.out.println("Saved " + employees.size() + " employees to file.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    private Employee parseEmployee(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length == 4) {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String department = parts[2].trim();
                double salary = Double.parseDouble(parts[3].trim());
                return new Employee(id, name, department, salary);
            }
        } catch (Exception e) {
            System.out.println("Error parsing line: " + csvLine);
        }
        return null;
    }
    
    private String employeeToCsv(Employee employee) {
        return employee.getId() + "," + 
               employee.getName() + "," + 
               employee.getDepartment() + "," + 
               employee.getSalary();
    }
}