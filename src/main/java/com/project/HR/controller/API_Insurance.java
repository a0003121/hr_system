package com.project.HR.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.HR.dao.AuthorityDAO;
import com.project.HR.dao.EmployeeDAO;
import com.project.HR.dao.InsuranceDAO;
import com.project.HR.dao.UserDAO;
import com.project.HR.vo.Authority;
import com.project.HR.vo.Employee;
import com.project.HR.vo.Insurance;
import com.project.HR.vo.User;


@RestController
public class API_Insurance {
	
	@Autowired
	InsuranceDAO insuranceDAO;
	
	@Autowired
	EmployeeDAO employeeDAO;
	
	@Autowired
	UserDAO userDAO;
	//////////*保險*///////////
	
	@GetMapping("/insurances")
	public List<Insurance> getInsurances(){
		return insuranceDAO.findAll();
	}
	
	@PutMapping("/insurance")
	public Employee updateEmployee(int id, int work, int health, int compensation) {
		Employee employee= employeeDAO.findById(id).get();
		
		employee.setCompensation(compensation);
		employee.setWorkInsurance(work);
		employee.setHealthInsurance(health);
		Employee result = null;
		try {
			result = employeeDAO.save(employee);
		} catch (DataIntegrityViolationException  e) {
			return new Employee();
		}
		return result;
	}
	
	
	@GetMapping("/auth")
	public List<User> getAuth(){
		return userDAO.findAll();
	}
	
	@Autowired
	AuthorityDAO authorityDAO;
	@GetMapping("/test")
	public List<Authority> getAuddth(){
		return authorityDAO.findAll();
	}
	
}
