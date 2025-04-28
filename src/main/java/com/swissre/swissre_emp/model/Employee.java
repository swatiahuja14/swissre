package com.swissre.swissre_emp.model;

import lombok.*;

@ToString
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Getter
    private int id;
    @Getter @Setter
    private String firstName;
    private String lastName;
    private int managerId;
    private int salary;

    public Employee(int id, String firstName, String lastName, int managerId, int salary){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerId = managerId;
        this.salary = salary;
    }

    public Employee(int id, String firstName, String lastName, int salary){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerId =0;
        this.salary = salary;
    }
}
