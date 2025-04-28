package com.swissre.swissre_emp.util;

import com.swissre.swissre_emp.cache.EmployeeCache;
import com.swissre.swissre_emp.cache.ManagerCache;
import com.swissre.swissre_emp.model.Employee;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileLoader {
    public static void main(String[] args){
        loadData();
    }
    public static void loadData() {
        System.out.println("Enter csv file path, else program will take mock data");
        EmployeeCache employeeCache = EmployeeCache.getInstance();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("MOCK_DATA3.csv"));
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFound "+e.getMessage());
            throw new RuntimeException(e);
        }

        Map<String, String> employeManagerMap = new HashMap<>();
        String line = null;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                System.err.println("IOException blank line: "+e.getMessage());
                throw new RuntimeException(e);
            }
            //System.out.println(line);
            Employee employee = createEmployee(line);
            if(employee !=null) {
                employeeCache.addEmployee(employee);
                employeManagerMap.put(employee.getId()+"",employee.getManagerId()+"");
            } else {
                continue;
            }
        }
        System.err.println("employeManagerMap: "+employeManagerMap.size());

        Map<String, Integer> employeeLevels = new HashMap<>();
        for(String emp :employeManagerMap.keySet()){
            int count =0;
            String mgr = employeManagerMap.get(emp);
            while(mgr!=null){
                count++;
                //System.out.println(emp+" "+mgr+" "+count);
                mgr = employeManagerMap.get(mgr);
            }
            if(count>4){
                employeeLevels.put(emp,count);
            }
        }
        System.out.println(employeeLevels.size());

        ManagerCache managerCache = new ManagerCache();
        managerCache.initialize(employeeCache);
        System.err.println("managerCache: "+managerCache.getCeo());

        List<Employee> e4 = managerCache.getEmployeesWithMoreThan4Levels(employeeCache);
        System.out.println("getEmployeesWithMoreThan4Levels " +e4.size() + " [ "+e4);

        List<Employee> m5 = managerCache.mgrsWithSalaryGT50();
        System.out.println("mgrsWithSalaryGT50 " +m5.size() + " [ "+m5);

        List<Employee> m2 = managerCache.mgrsWithSalaryLT20();
        System.out.println("mgrsWithSalaryLT20 " +m2.size() + " [ "+m2);

    }

    private static Employee createEmployee(String line) {
        String[] employeString = line.trim().split(",");
        if(employeString.length<4){
            return null;
        }
        try{
            Integer.parseInt(employeString[0]);
        } catch (Exception e){
            throw new RuntimeException("Invalid Id, Id should only be int");
        }

        try{
            Integer.parseInt(employeString[3]);
        } catch (Exception e){
            throw new RuntimeException("Invalid salary, salary should only be int/whole numbers");
        }

        /*try{
            Integer.parseInt(employeString[4]);
        } catch (Exception e){
            throw new RuntimeException("Invalid Mgr Id, Id should only be int");
        }*/
        
        String mgrid = employeString.length>=5?employeString[4]:"0";
        return new Employee(Integer.parseInt(employeString[0]), employeString[1], employeString[2], Integer.parseInt(mgrid), Integer.parseInt(employeString[3]));
    }
}
