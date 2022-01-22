package com.project.HR.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	public Employee createEmployee(Employee employeeData, String leaveDateN, String salaryN) throws ParseException {
		if(!"".equals(salaryN.trim())) {
			employeeData.setSalary(Integer.parseInt(salaryN));
		}
		if(!"".equals(leaveDateN.trim())) {
			java.util.Date date =new SimpleDateFormat("yyyy-MM-dd").parse(leaveDateN);
			employeeData.setLeaveDate(new Date(date.getTime()));
		}
		
		Employee temp = null;
		try {
			temp = employeeDAO.save(employeeData);
		} catch (DataIntegrityViolationException e) {
			return new Employee();
		}
		
		return temp;
	}

	@PutMapping("/employee")
	public Employee updateEmployee(Employee employeeData, String leaveDateN, String salaryN) throws ParseException {
		Employee result = null;
		
		if(!"".equals(salaryN.trim())) {
			employeeData.setSalary(Integer.parseInt(salaryN));
		}
		if(!"".equals(leaveDateN.trim())) {
			java.util.Date date =new SimpleDateFormat("yyyy-MM-dd").parse(leaveDateN);
			employeeData.setLeaveDate(new Date(date.getTime()));
		}
		try {
			result = employeeDAO.save(employeeData);
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