package com.project.HR.controller;

import com.project.HR.dao.DeptDAO;
import com.project.HR.dao.LeaveDAO;
import com.project.HR.vo.Dept;
import com.project.HR.vo.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@EnableJpaAuditing//啟用審計(Auditing)
public class API_GeneralLeave {
	@Autowired
	LeaveDAO leaveDAO;
	
	//////////*假別*///////////
	@GetMapping("/generalLeaves")
	public ResponseEntity<List<Leave>> getAllGeneralLeaves(){
		return ResponseEntity.ok(leaveDAO.findAll());
	}
	
	@GetMapping("/generalLeave")
	public ResponseEntity<Leave> getGeneralLeave(String id){
		return ResponseEntity.ok(leaveDAO.findById(Integer.parseInt(id)).get());
	}
	
	@PostMapping("/generalLeave")
	public ResponseEntity<Leave> createGeneralLeave (String name, int days, float count) {
		Leave leave = new Leave();
		leave.setName(name);
		leave.setDay(days);
		leave.setSalaryCount(count);
		return ResponseEntity.ok(leaveDAO.save(leave));
	}
	
	@PutMapping("/generalLeave")
	public ResponseEntity<Leave> updateGeneralLeave(int id, String name, int days, float count) {
		Leave leave = new Leave();
		leave.setId(id);
		leave.setName(name);
		leave.setSalaryCount(count);
		leave.setDay(days);
		return ResponseEntity.ok(leaveDAO.save(leave));
	}
	
	@DeleteMapping("/generalLeave")
	public ResponseEntity<String> deleteGeneralLeave(String id) {
		leaveDAO.deleteById(Integer.parseInt(id));
		return ResponseEntity.ok("{\"delete\":\"success!\"}");
	}
	
}
