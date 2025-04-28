package com.swissre.swissre_emp.cache;

import com.swissre.swissre_emp.model.Employee;

import java.util.*;

public class EmployeeCache {
    private Map<Integer, Employee> employeeMap;
    private static EmployeeCache instance;

    private EmployeeCache(){
        this.employeeMap= new HashMap<>();
    }

    public void addEmployee(Employee e){
        this.employeeMap.put(e.getId(), e);
    }

    public Collection<Employee> getAll(){
        return employeeMap.values();
    }
    public Employee getEmployee(int id){
        return this.employeeMap.get(id);
    }

    public static EmployeeCache getInstance(){
        if(instance == null){
            instance = new EmployeeCache();
        }
        return instance;
    }


}

