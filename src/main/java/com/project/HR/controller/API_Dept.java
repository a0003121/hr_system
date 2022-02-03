package com.project.HR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.HR.dao.DeptDAO;
import com.project.HR.vo.Dept;

@RestController
//@EnableJpaAuditing//啟用審計(Auditing)
public class API_Dept {
	@Autowired
	DeptDAO deptDAO;
	
	//////////*部門*///////////
	@GetMapping("/depts")
	public List<Dept> getAllDepts(){
		return deptDAO.findAll();
	}
	
	@GetMapping("/dept")
	public Dept getOneDept(String id){
		return deptDAO.findById(Integer.parseInt(id)).get();
	}
	
	@PostMapping("/dept")
	public Dept createDept (String name) {
		Dept dept = new Dept();
		dept.setId(0);
		dept.setName(name);
		return deptDAO.save(dept);
	}
	
	@PutMapping("/dept")
	public Dept updateDept(int id, String name) {
		Dept dept = new Dept();
		dept.setId(id);
		dept.setName(name);
		return deptDAO.save(dept);
	}
	
	@DeleteMapping("/dept")
	public String deleteDept(String id) {
		deptDAO.deleteById(Integer.parseInt(id));
		return "{\"delete\":\"success!\"}";
	}
	
}
