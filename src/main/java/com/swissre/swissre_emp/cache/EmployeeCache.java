package com.swissre.swissre_emp.cache;

import com.swissre.swissre_emp.model.Employee;
import org.springframework.context.annotation.Bean;

import java.util.*;

public class EmployeeCache {
    private static EmployeeCache instance;

    private Map<Integer, Employee> employeeMap;
    public Map<Employee, List<Employee>> managerReporteesMap;

    private EmployeeCache(){
        this.employeeMap= new HashMap<>();
    }

    public Collection<Employee> getAll(){
        return employeeMap.values();
    }

    public void addEmployee(Employee e){
        this.employeeMap.put(e.getId(), e);
    }

    public Employee getEmployee(int id){
        return this.employeeMap.get(id);
    }

    public List<Employee> getReportees(int id){
        return this.managerReporteesMap.get(employeeMap.get(id));
    }

    public List<Employee> getReportees(Employee employee){
        return this.managerReporteesMap.get(employee);
    }

    public static EmployeeCache getInstance(){
        if(instance == null){
            instance = new EmployeeCache();
        }
        return instance;
    }


}

