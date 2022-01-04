package com.project.HR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.HR.dao.DeptDAO;
import com.project.HR.dao.InsuranceDAO;
import com.project.HR.vo.Dept;
import com.project.HR.vo.Insurance;


@RestController
public class JSONController_Insurance {
	
	@Autowired
	InsuranceDAO insuranceDAO;
	
	//////////*保險*///////////
	
	@GetMapping("/insurances")
	public List<Insurance> getInsurances(){
		return insuranceDAO.findAll();
	}
	
}
