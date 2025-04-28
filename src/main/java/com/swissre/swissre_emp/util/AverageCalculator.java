package com.swissre.swissre_emp.util;

import com.swissre.swissre_emp.model.Employee;

import java.util.List;

public class AverageCalculator {
    public static double findAverageSalary(List<Employee> employeeList){
        return employeeList.stream().mapToInt(Employee::getSalary).average().getAsDouble();
    }
}
