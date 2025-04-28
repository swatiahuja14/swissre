package com.swissre.swissre_emp.util;

import com.swissre.swissre_emp.cache.EmployeeCache;
import com.swissre.swissre_emp.model.Employee;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileLoader {

    public static void loadData() {
        System.err.println("Enter csv file path, else press Enter/any key for program to take mock data");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();

        if(filename==null || filename.isEmpty()){
            filename = "MOCK_DATA2.csv";
            System.out.println("Filename not provided program is taking " +
                    "mock data from "+filename);
        }
        EmployeeCache employeeCache = EmployeeCache.getInstance();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFound "+e.getMessage());
            throw new RuntimeException(e);
        }

        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                System.err.println("IOException blank line: "+e.getMessage());
                //throw new RuntimeException(e);
                continue;
            }
            //System.out.println(line);
            Employee employee = EmployeeValidator.validateAndCreateEmployee(line);
            if(employee !=null) {
                employeeCache.addEmployee(employee);
            }
        }
        System.err.println("employeeCache: "+employeeCache.getAll().size());
    }
}
