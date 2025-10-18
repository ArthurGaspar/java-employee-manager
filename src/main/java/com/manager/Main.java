package com.manager;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final int PAGE_SIZE = 5;
    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== Employee Management System ===");
            System.out.println("1. View employees by ID");
            System.out.println("2. Add new employee");
            System.out.println("3. View employees by department");
            System.out.println("4. Find highest paid employee");
            System.out.println("5. Calculate average salary");
            System.out.println("6. View employees alphabetically");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    List<Employee> allEmployees = service.getAllEmployees();
                    PaginationUtil.paginate(allEmployees, "All Employees", PAGE_SIZE, scanner);
                    break;
                    
                case 2:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    
                    service.getAddEmployee(new Employee(id, name, dept, salary));
                    System.out.println("Employee added successfully!");
                    break;
                    
                case 3:
                    List<String> departments = service.getAllDepartments();
                    if (departments.isEmpty()) {
                        System.out.println("No departments found. Please add employees first.");
                    } else {
                        System.out.println("Available departments: " + departments);
                        System.out.print("Enter department: ");
                        String department = scanner.nextLine();
                        
                        List<Employee> deptEmployees = service.getEmployeesByDepartment(department);
                        if (deptEmployees.isEmpty()) {
                            System.out.println("No employees found in " + department + " department.");
                        } else {
                            PaginationUtil.paginate(deptEmployees, 
                                "Employees in " + department + " Department", 
                                PAGE_SIZE, scanner);
                        }
                    }
                    break;
                    
                case 4:
                    Optional<Employee> highestPaid = service.getHighestPaidEmployee();
                    highestPaid.ifPresentOrElse(
                        emp -> System.out.println("Highest paid: " + emp),
                        () -> System.out.println("No employees found")
                    );
                    break;
                    
                case 5:
                    System.out.println("Average salary: $" + service.getAverageSalary());
                    break;
                    
                case 6:
                    List<Employee> sortedEmployees = service.getEmployeesSortedByName();
                    PaginationUtil.paginate(sortedEmployees, 
                        "Employees Sorted by Name", PAGE_SIZE, scanner);
                    break;
                    
                case 7:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}