package com.swissre.swissre_emp.util;

import com.swissre.swissre_emp.cache.EmployeeCache;
import com.swissre.swissre_emp.model.Employee;

import java.io.*;
import java.util.*;

public class DataGenerator {
    public static void main(String[] args){
        try {
            loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static int id = 1;

    public static List<Employee> createEmployees(int parent, int level, List<String> fNames, List<String> lnames){
        if(level >=8 || id>=1000){
            return new ArrayList<>();
        }
        List<Employee> ids = new ArrayList<>();
        //int id =0;
        int count = (int) Math.abs(Math.random() *Math.pow(10,1) );
        //System.out.println(count+" count at level "+level);
        for(int k=0; k<count; k++) {
            id++; // = (level * 100) + k;
            int l = 10-level;
            int salary = (l*10000) + (id*10);
            Employee e = new Employee(id,fNames.get(id),lnames.get(id),parent, salary);
            //System.out.println(e);
            ids.add(e);
            //  this id 's parent is parent
        }
        //
        return ids;
    }
    public static void loadData() throws IOException {

       /* if(in.hasNext()) {
            path = in.next();
        } else {
            path = "//MOCK_DATA.csv";
        }*/
        //File f = new File(path);
        Map<String, String> employeSalaryMap = new HashMap<>();
        Map<String, String> employeManagerMap = new HashMap<>();
        EmployeeCache employeeCache = EmployeeCache.getInstance();
        BufferedReader reader = new BufferedReader(new FileReader("MOCK_DATA1.csv"));
        List<String> lines = new ArrayList<>();
        List<String> fnames = new ArrayList<>();
        List<String> lnames = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
            //System.out.println(line);
            String [] employeString =line.trim().split(",");
            fnames.add(employeString[1]);
            lnames.add(employeString[2]);
    /*        System.out.println(employeString[0]+ " " + employeString[1]+" "+employeString[3]+" "+employeString.length);
            employeManagerMap.put(employeString[0],employeString.length>=5?employeString[4]:"");
            employeSalaryMap.put(employeString[0], employeString[3]);*/

            //employeeCache.addEmployee(new Employee(employeString[0],employeString[1],employeString[2], employeString[3],employeString[4]));
        }

        System.out.println("Enter csv file path, else program will take mock data");

        BufferedWriter writer = new BufferedWriter(new FileWriter("MOCK_DATA3.csv"));
       // Scanner in = new Scanner(System.in);
      //  String path = "MOCK_DATA.csv"; int id =0;
        int parent = 1;
        List<Employee> emps = new ArrayList<>();
        int i =0;
            List<Employee> empsAtThisLevel = createEmployees(parent,1, fnames, lnames);
            emps.addAll(empsAtThisLevel);
            i++;
            for(Employee emk:empsAtThisLevel){
                List<Employee> newemps = createEmployees(emk.getId(), 2, fnames, lnames);
                emps.addAll(newemps);

                for(Employee emk1:newemps){
                    List<Employee> newemps1 = createEmployees(emk1.getId(), 3, fnames, lnames);
                    emps.addAll(newemps1);

                    for(Employee emk2:newemps1){
                        List<Employee> newemps2 = createEmployees(emk2.getId(), 4, fnames, lnames);
                        emps.addAll(newemps2);

                        for(Employee emk32:newemps2){
                            List<Employee> newemps3 = createEmployees(emk32.getId(), 5, fnames, lnames);
                            emps.addAll(newemps3);
/*
                            for(Employee emk3:newemps3){
                                List<Employee> newemps4 = createEmployees(emk3.getId(), 6, fnames, lnames);
                                emps.addAll(newemps4);
                            }*/

                        }


                    }
                }
            }

            for(Employee e: emps){
              //  System.out.println(e);
                writer.write(e.getId()+","+e.getFirstName()+","+e.getLastName()+","+e.getSalary()+","+e.getManagerId());
                writer.newLine();
                employeeCache.addEmployee(e);
            }
            writer.write(1+", James, Hollick,"+2500000);
            writer.newLine();

            writer.flush();
            writer.close();


       System.out.println(emps.size());
      /*  Map<String, Integer> employeeLevels = new HashMap<>();
        for(String emp :employeManagerMap.keySet()){
            int count =0;
            String mgr = employeManagerMap.get(emp);
            while(mgr!=null){
                count++;
                System.out.println(emp+" "+mgr+" "+count);
                mgr = employeManagerMap.get(mgr);
            }
            if(count>4){
                employeeLevels.put(emp,count);
            }
        }
        System.out.println(employeeLevels);*/


    }
}
