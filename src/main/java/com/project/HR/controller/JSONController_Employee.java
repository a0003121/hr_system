package com.project.HR.controller;

import java.sql.Date;
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

@RestController
public class JSONController_Employee {
	@Autowired
	EmployeeDAO employeeDAO;
	
	//////////*員工*///////////
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeDAO.findAll();
	}
	
	@GetMapping("/employee")
	public Employee getOneEmployee(int id) {
		return employeeDAO.findById(id).get();
	}
	
	@PostMapping("/employee")
	public Employee createEmployee(String name, int position,int empNo, int dept, Date hireDate, String salary) {
		Employee employee= new Employee();
		employee.setId(0);
		employee.setEmpNo(empNo);
		employee.setName(name);
		employee.setPosition(position);
		employee.setDept(dept);
		employee.setHireDate(hireDate);
		if("".equals(salary)) {
			employee.setSalary(null);
		}else {
			employee.setSalary(Integer.parseInt(salary));			
		}
		Employee temp = null;
		try {
			temp = employeeDAO.save(employee);
			
		} catch (DataIntegrityViolationException  e) {
			return new Employee();
		}
		return temp;
	}
	
	@PutMapping("/employee")
	public Employee updateEmployee(int id, String name, String position, int empNo, String dept, Date hireDate, int salary) {
		Employee employee= new Employee();
		employee.setId(id);
		employee.setEmpNo(empNo);
		employee.setName(name);
		employee.setPosition(Integer.parseInt(position));
		employee.setDept(Integer.parseInt(dept));
		employee.setHireDate(hireDate);
		employee.setSalary(salary);
		Employee result = null;
		try {
			result = employeeDAO.save(employee);
		} catch (DataIntegrityViolationException  e) {
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
