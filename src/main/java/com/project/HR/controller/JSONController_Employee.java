package com.project.HR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.HR.dao.EmployeeDAO;
import com.project.HR.vo.Employee;
import com.project.HR.vo.EmployeeForm;

@RestController
public class JSONController_Employee {
	@Autowired
	EmployeeDAO employeeDAO;

	////////// *員工*///////////
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeDAO.findAllByOrderByEmpNoAsc();
	}

	@GetMapping("/employee")
	public Employee getOneEmployee(int id) {
		return employeeDAO.findById(id).get();
	}

	@PostMapping("/employee")
	public Employee createEmployee(EmployeeForm employeeData) {
		Employee employee = Employee.builder()
									.id(employeeData.getId())
									.empNo(employeeData.getEmpNo())
									.name(employeeData.getName())
									.position(employeeData.getPosition())
									.dept(employeeData.getDept())
									.hireDate(employeeData.getHireDate())
									.build();
		
		if ("".equals(employeeData.getSalary())) 
			employee.setSalary(null);
		else 
			employee.setSalary(Integer.parseInt(employeeData.getSalary()));
		
		Employee temp = null;
		try {
			temp = employeeDAO.save(employee);
		} catch (DataIntegrityViolationException e) {
			return new Employee();
		}
		
		return temp;
	}

	@PutMapping("/employee")
	public Employee updateEmployee(EmployeeForm employeeData) {
		Employee employee = Employee.builder()
									.id(employeeData.getId())
									.empNo(employeeData.getEmpNo())
									.name(employeeData.getName())
									.position(employeeData.getPosition())
									.dept(employeeData.getDept())
									.hireDate(employeeData.getHireDate())
									.salary(Integer.parseInt(employeeData.getSalary()))
									.build();
		
		Employee result = null;
		try {
			result = employeeDAO.save(employee);
		} catch (DataIntegrityViolationException e) {
			return new Employee();
		}
		return result;
	}

	@DeleteMapping("/employee")
	public String deleteEmployee(int id) {
		employeeDAO.deleteById(id);
		return "{\"delete\":\"success!\"}";
	}
}
