package com.swissre.swissre_emp.service;

import com.swissre.swissre_emp.cache.EmployeeCache;
import com.swissre.swissre_emp.model.Employee;
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
                    System.out.println(e.getFullName()+", extra levels:"+(e.getLevelFromCEO()-level) + " "+e);
                }
            }
        }
        return employeeList;
    }

    public List<Employee> mgrsWithSalaryGT50(boolean print){
        List<Employee> mgrs = new ArrayList<>();
        for(Employee e: employeeCache.getAll()){
            if(e.getAvgSalaryOfDirectReportees()>0) {
                double max = 1.5 * e.getAvgSalaryOfDirectReportees();
                //System.out.println(e.getFullName()+ " sal:"+e.getSalary()+" avg:"+e.getAvgSalaryOfDirectReportees() + " max:"+max);
                if (e.getSalary() > max) {
                    mgrs.add(e);
                    //System.out.println("---->"+e.getFullName()+ " sal:"+e.getSalary()+" avg:"+e.getAvgSalaryOfDirectReportees() + " max:"+max);

                    if (print) {
                        double diff = e.getSalary() - max;
                        System.out.println(e.getFullName()+ ", difference above 50%:" + diff + " "+e);
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
                double min = 1.2 * e.getAvgSalaryOfDirectReportees();
                //System.out.println(e.getFullName()+ " sal:"+e.getSalary()+" avg:"+e.getAvgSalaryOfDirectReportees() + " min:"+min);
                if (e.getSalary() < min) {
                    mgrs.add(e);
                    //System.out.println(">>>  "+e.getFullName()+ " sal:"+e.getSalary()+" avg:"+e.getAvgSalaryOfDirectReportees() + " min:"+min);

                    if (print) {
                        double diff = (min - e.getSalary());
                        System.out.println(e.getFullName() + ", difference less than 120% :" + diff + " "+e);
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
