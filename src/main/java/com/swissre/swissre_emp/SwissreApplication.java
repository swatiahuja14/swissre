package com.swissre.swissre_emp;

import com.swissre.swissre_emp.service.EmployeeService;
import com.swissre.swissre_emp.util.EmployeeCalculationEngine;
import com.swissre.swissre_emp.util.FileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@EnableAutoConfiguration
@SpringBootApplication
public class SwissreApplication implements CommandLineRunner {

	@Autowired
	EmployeeService employeeService;

	public static void main(String[] args) {
		SpringApplication.run(SwissreApplication.class, args);
	}

	@Override
	public void run(String... arg) {
		System.out.println("EXECUTING : command line runner");
		FileLoader.loadData();

		EmployeeCalculationEngine employeeCalculationEngine = new EmployeeCalculationEngine();
		employeeCalculationEngine.initializeEmployeeValues();

		System.err.println("Enter false to display numbers only, else press any key for program to print employees details");
		Scanner scanner = new Scanner(System.in);
		boolean flagToPrint = true;
		try {
			flagToPrint = scanner.nextBoolean();
		} catch (Exception e){
			System.out.println("System will print details of employees");
		}

		System.err.print("Employees who have a reporting line which is too long, No of Employees With More Than 4 Levels from CEO: ");
		System.err.print(employeeService.getEmployeesWithMoreThanNLevels(4, flagToPrint).size());
		System.err.println();
		System.err.println("---------------");



		System.err.print(" managers earn less than they should, No of Employees Managers With Salary Less than 20% more than avg reportees: ");
		System.err.print(employeeService.mgrsWithSalaryLT20(flagToPrint).size());
		System.err.println();
		System.err.println("---------------");


		System.err.print(" managers who earn more than they should, No of Employees Managers With Salary More than 50% of avg reportees: ");
		System.err.print(employeeService.mgrsWithSalaryGT50(flagToPrint).size());
		System.err.println();
		System.err.println("---------------");


		System.exit(0);
	}

}
