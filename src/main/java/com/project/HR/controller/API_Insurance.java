package com.project.HR.controller;

import com.project.HR.dao.EmployeeDAO;
import com.project.HR.dao.InsuranceDAO;
import com.project.HR.dao.UserDAO;
import com.project.HR.vo.Employee;
import com.project.HR.vo.Insurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
	public ResponseEntity<List<Insurance>> getInsurances(){
		return ResponseEntity.ok(insuranceDAO.findAll());
	}
	
	@PutMapping("/insurance")
	public ResponseEntity<Employee> updateEmployee(int id, int work, int health, int compensation) {
		Employee employee= employeeDAO.findById(id).get();
		
		employee.setCompensation(compensation);
		employee.setWorkInsurance(work);
		employee.setHealthInsurance(health);
		Employee result = null;
		try {
			result = employeeDAO.save(employee);
		} catch (DataIntegrityViolationException  e) {
			return ResponseEntity
					.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new Employee());
		}
		return ResponseEntity.ok(result);
	}
	
}
