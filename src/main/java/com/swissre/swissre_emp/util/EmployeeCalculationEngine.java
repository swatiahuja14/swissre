package com.swissre.swissre_emp.util;

import com.swissre.swissre_emp.cache.EmployeeCache;
import com.swissre.swissre_emp.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeCalculationEngine {

    // EmployeeCalculationEngine
    private Map<Employee, List<Employee>> managerReporteesMap;

    EmployeeCache employeeCache = EmployeeCache.getInstance();

    public synchronized void initializeEmployeeValues(){
        managerReporteesMap = new HashMap<>();
        generateManagerReporteesMap();
        calculateEmployeeLevels();
        calculateAvgSalaryOfReportees();
    }

    private synchronized void calculateAvgSalaryOfReportees() {
        if(!managerReporteesMap.isEmpty()) {
            for (Employee mgr : managerReporteesMap.keySet()) {
                double avg = AverageCalculator.findAverageSalary(managerReporteesMap.get(mgr));
                mgr.setAvgSalaryOfDirectReportees(avg);
            }
        }
    }

    private synchronized void calculateEmployeeLevels() {
        for(Employee e: employeeCache.getAll()) {
            int level = 0;
            int mgrId = e.getManagerId();
            Employee mgr = employeeCache.getEmployee(mgrId);
            while (mgr != null) {
                level++;
                mgr = employeeCache.getEmployee(mgr.getManagerId());
            }
            e.setLevelFromCEO(level);
        }
    }

    private synchronized void generateManagerReporteesMap() {
        for(Employee e: employeeCache.getAll()) {
            List<Employee> reportees = new ArrayList<>();
            if (e.getManagerId() != 0) {
                Employee mgr = employeeCache.getEmployee(e.getManagerId());
                if(mgr == null && !e.isCEO()){
                    System.err.println("Data Error, employee manager not in data "+e);
                    continue;
                }
                if (managerReporteesMap.containsKey(mgr)) {
                    reportees.addAll(managerReporteesMap.get(mgr));
                }
                reportees.add(e);
                managerReporteesMap.put(mgr, reportees);
            }
        }
        employeeCache.managerReporteesMap = this.managerReporteesMap;
    }

}
