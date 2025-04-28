package com.swissre.swissre_emp.cache;

import com.swissre.swissre_emp.model.Employee;
import com.swissre.swissre_emp.util.AverageCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerCache {
    private Map<Employee, List<Employee>> managerReporteesMap;
    private Employee ceo;
    public Employee getCeo(){
        return this.ceo;
    }
    public void initialize(EmployeeCache employeeCache){
        managerReporteesMap = new HashMap<>();
        for(Employee e: employeeCache.getAll()) {
            List<Employee> reportees = new ArrayList<>();
            if(e.getManagerId() != 0) {
                Employee mgr = employeeCache.getEmployee(e.getManagerId());
                if (managerReporteesMap.containsKey(mgr)) {
                    reportees.addAll(managerReporteesMap.get(mgr));
                }
                reportees.add(e);
                managerReporteesMap.put(mgr, reportees);
            } else {
                ceo = e;
            }
        }
    }

    public List<Employee> mgrsWithSalaryGT50(){
        List<Employee> mgrs = new ArrayList<>();
        if(!managerReporteesMap.isEmpty()) {
            for (Employee mgr : managerReporteesMap.keySet()) {
                double avg = AverageCalculator.findAverageSalary(managerReporteesMap.get(mgr));
                //System.out.println(mgr);
               // System.out.println(avg);
               // System.out.println(managerReporteesMap.get(mgr));
                if (mgr.getSalary() > (1.5 * avg)) {
                    mgrs.add(mgr);
                }
            }
        }
        return mgrs;
    }

    public List<Employee> mgrsWithSalaryLT20(){
        List<Employee> mgrs = new ArrayList<>();
        for(Employee mgr: managerReporteesMap.keySet()){
            double avg = AverageCalculator.findAverageSalary(managerReporteesMap.get(mgr));
            if(mgr.getSalary() > (1.2*avg)){
                mgrs.add(mgr);
            }
        }
        return mgrs;
    }


    public List<Employee> getEmployeesWithMoreThan4Levels(EmployeeCache employeeCache){
        List<Employee> employeeList = new ArrayList<>();
        for(Employee e: employeeCache.getAll()){
            int count =0;
            int mgrId = e.getManagerId();
            Employee mgr = employeeCache.getEmployee(mgrId);
            while (mgr != null){
                count++;
                mgr = employeeCache.getEmployee(mgr.getManagerId());
            }
            if(count>=5){
                employeeList.add(e);
            }
        }
        return employeeList;
    }

}
