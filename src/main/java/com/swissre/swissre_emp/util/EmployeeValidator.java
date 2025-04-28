package com.swissre.swissre_emp.util;

import com.swissre.swissre_emp.model.Employee;

public class EmployeeValidator {
    public static Employee validateAndCreateEmployee(String line) {
        if(line == null || line.isEmpty()){
            System.err.println("Invalid empty/blank/null line");
            return null;
        }

        String[] employeString = line.trim().split(",");

        if(employeString.length<4){
            System.err.println("Invalid entry in csv file:"+line);
            return null;
        }

        try{
            Integer.parseInt(employeString[0]);
        } catch (Exception e){
            //throw new RuntimeException("Invalid Id, Id should only be int");
           System.err.println("Invalid Id or First row "+employeString[0]);
           return null;
        }

        try{
            Integer.parseInt(employeString[3]);
        } catch (Exception e){
            //throw new RuntimeException("Invalid salary, salary should only be int/whole numbers");
            System.err.println("Invalid salary, salary should only be int/whole numbers");
            return null;
        }

        if(employeString.length>4){
            try{
                Integer.parseInt(employeString[4]);
            } catch (Exception e){
                //throw new RuntimeException("Invalid Mgr Id, Id should only be int");
                System.err.println("Invalid Mgr Id, Id should only be int");
                return null;
            }
        }

        String mgrid = employeString.length>=5?employeString[4]:"0";
        Employee e = new Employee(Integer.parseInt(employeString[0]), employeString[1], employeString[2], Integer.parseInt(mgrid), Integer.parseInt(employeString[3]));
        if(mgrid.equals("0")){
            e.setCEO(true);
        }
        return e;
    }
}
