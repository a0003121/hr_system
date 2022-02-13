package com.project.HR.controller;

import com.project.HR.dao.DeptDAO;
import com.project.HR.vo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@EnableJpaAuditing//啟用審計(Auditing)
public class API_Dept {
	@Autowired
	DeptDAO deptDAO;
	
	//////////*部門*///////////
	@GetMapping("/depts")
	public ResponseEntity<List<Dept>> getAllDepts(){
		return ResponseEntity.ok(deptDAO.findAll());
	}
	
	@GetMapping("/dept")
	public ResponseEntity<Dept> getOneDept(String id){
		return ResponseEntity.ok(deptDAO.findById(Integer.parseInt(id)).get());
	}
	
	@PostMapping("/dept")
	public ResponseEntity<Dept> createDept (String name) {
		Dept dept = new Dept();
		dept.setId(0);
		dept.setName(name);
		return ResponseEntity.ok(deptDAO.save(dept));
	}
	
	@PutMapping("/dept")
	public ResponseEntity<Dept> updateDept(int id, String name, Integer empNo) {
		Dept dept = new Dept();
		dept.setId(id);
		dept.setName(name);
		dept.setMgEmpNo(empNo);
		return ResponseEntity.ok(deptDAO.save(dept));
	}
	
	@DeleteMapping("/dept")
	public ResponseEntity<String> deleteDept(String id) {
		deptDAO.deleteById(Integer.parseInt(id));
		return ResponseEntity.ok("{\"delete\":\"success!\"}");
	}
	
}
