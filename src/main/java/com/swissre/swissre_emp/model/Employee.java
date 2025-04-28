package com.swissre.swissre_emp.model;

import lombok.*;

@ToString
@Getter
@Setter
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int managerId;
    private int salary;
    private int levelFromCEO=0;
    private double avgSalaryOfDirectReportees=0;
    private boolean isCEO;

    public Employee(int id, String firstName, String lastName, int managerId, int salary){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerId = managerId;
        this.salary = salary;
    }

    public String getFullName(){
        return this.firstName+" "+this.lastName;
    }
}
