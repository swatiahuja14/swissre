package com.swissre.swissre_emp.service;

import com.swissre.swissre_emp.cache.EmployeeCache;
import com.swissre.swissre_emp.util.EmployeeCalculationEngine;
import com.swissre.swissre_emp.model.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    EmployeeCache employeeCache = EmployeeCache.getInstance();

    /*
    @PostConstruct
    private void init() {
        EmployeeCalculationEngine ec = new EmployeeCalculationEngine();
        ec.initializeEmployeeValues();
    }*/

    public List<Employee> getEmployeesWithMoreThanNLevels(int level, boolean print){
        List<Employee> employeeList = new ArrayList<>();
        for(Employee e: employeeCache.getAll()){
            if(e.getLevelFromCEO()>level){
                employeeList.add(e);
                if(print){
                    System.out.println(e.getFullName()+", levels:"+e.getLevelFromCEO());
                }
            }
        }
        return employeeList;
    }

    public List<Employee> mgrsWithSalaryGT50(boolean print){
        List<Employee> mgrs = new ArrayList<>();
        for(Employee e: employeeCache.getAll()){
            if(e.getAvgSalaryOfDirectReportees()>0) {
                double diff = e.getSalary() - 1.5 * e.getAvgSalaryOfDirectReportees();
                if (e.getSalary() > (1.5 * e.getAvgSalaryOfDirectReportees())) {
                    mgrs.add(e);
                    if (print) {
                        System.out.println(e.getFullName()+ ", difference above 50%:" + diff);
                    }
                }
            }
        }
        return mgrs;
    }

    public List<Employee> mgrsWithSalaryLT20(boolean print) {
        List<Employee> mgrs = new ArrayList<>();
        for (Employee e : employeeCache.getAll()) {
            if(e.getAvgSalaryOfDirectReportees()>0) {
                double diff = (1.2 * e.getAvgSalaryOfDirectReportees() - e.getSalary());
                if (e.getSalary() < (1.2 * e.getAvgSalaryOfDirectReportees())) {
                    mgrs.add(e);
                    if (print) {
                        System.out.println(e.getFullName() + ", difference less than 120% :" + diff);
                    }
                }
            }
        }
        return mgrs;
    }

    public List<Employee> getReportees(int id){
        return employeeCache.getReportees(id);
    }

    public List<Employee> getReportees(Employee employee){
        return employeeCache.getReportees(employee);
    }

    public void prettyPrint(List<Employee> employees){
        for(Employee employee:employees){
            System.out.println(employee.getFullName()+"[id:"+employee.getId()+"]");
        }
    }
}
