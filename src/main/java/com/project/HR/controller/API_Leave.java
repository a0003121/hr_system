package com.project.HR.controller;

import com.project.HR.dao.LeaveDAO;
import com.project.HR.vo.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class API_Leave {
	
	@Autowired
	LeaveDAO leaveDAO;
	
	//////////*職位*///////////
	@GetMapping("/leaves")
	public ResponseEntity<List<Leave>> getAllLeaves(){
		return ResponseEntity.ok(leaveDAO.findAll());
	}
	
	@GetMapping("/leave")
	public ResponseEntity<Leave> getOneLeave(String id){
		return ResponseEntity.ok(leaveDAO.findById(Integer.parseInt(id)).get());
	}
	
	@PostMapping("/leave")
	public ResponseEntity<Leave> createLeave(String name, int day){
		Leave leave = new Leave();
		leave.setId(0);
		leave.setName(name);
		leave.setDay(day);
		return ResponseEntity.ok(leaveDAO.save(leave));
	}
	
	@PutMapping("/leave")
	public ResponseEntity<Leave> updateLeave(int id, String name, int day){
		Leave leave = new Leave();
		leave.setId(0);
		leave.setName(name);
		leave.setDay(day);
		return ResponseEntity.ok(leaveDAO.save(leave));
	}
	
	@DeleteMapping("/leave")
	public ResponseEntity<String> deletePosition(int id){
		leaveDAO.deleteById(id);
		return ResponseEntity.ok("{\"delete\":\"success!\"}");
	}
	
}
