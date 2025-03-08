package com.example.springbootapp.util;

import java.util.List;

import com.example.springbootapp.repository.CustomRepository;
import org.springframework.stereotype.Component;

import com.example.springbootapp.model.EmployeeDetails;
import com.example.springbootapp.repository.EmployeeRepository;

@Component
public class DBUtil {

    final EmployeeRepository employeeRepository;

    final CustomRepository customRepository;

    public DBUtil(EmployeeRepository employeeRepository, CustomRepository customRepository) {
        this.employeeRepository = employeeRepository;
        this.customRepository = customRepository;
    }

    public void createEmployee(String id, String name, int age, String education) {
		System.out.println("Data creation started...");
        employeeRepository.save(new EmployeeDetails(id, name, age, education));
		System.out.println("Data creation complete...");
	}

    public void updateEmployeeAge(String name, int age) {
        System.out.println("Data update started...");
        customRepository.updateEmployeeAge(name, age);
        System.out.println("Data update complete...");
    }

    public void showAllEmployees() {
        List <EmployeeDetails> employeeDetails = employeeRepository.findAll();
        employeeDetails.forEach(
            item -> System.out.println(getEmployeeDetail(item)));
    }

    public void getEmployeeByName(String name) {
        System.out.println("Getting employee by name: " + name);
        EmployeeDetails employeeDetails = employeeRepository.findEmployeeByName(name);
        System.out.println(getEmployeeDetail(employeeDetails));
    }

    public void getEmployeesByEducation(String education) {
        System.out.println("Getting employees by education " + education);
        List<EmployeeDetails> list = employeeRepository.findAll(education);
        list.forEach(item -> System.out.println(getEmployeeDetail(item, education)));
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
        System.out.println("Employee with id " + id + " deleted...");
    }

    public void findCountOfEmployees() {
        long count = employeeRepository.count();
        System.out.println("Number of employees = " + count);
    }

    private String getEmployeeDetail(EmployeeDetails employeeDetails) {
        return "EmployeeDetails [id=" + employeeDetails.getId() 
            + ", name=" + employeeDetails.getName()
            + ", age=" + employeeDetails.getAge() 
            + ", education=" + employeeDetails.getEducation() + "]";
    }

    private String getEmployeeDetail(EmployeeDetails employeeDetails, String education) {
        return "EmployeeDetails [id=" + employeeDetails.getId() 
            + ", name=" + employeeDetails.getName()
            + ", age=" + employeeDetails.getAge() 
            + ", education=" + education + "]";
    }
}