package com.project.HR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.HR.dao.LeaveDAO;
import com.project.HR.dao.PositionDAO;
import com.project.HR.vo.Leave;
import com.project.HR.vo.Position;

@RestController
public class JSONController_Leave {
	
	@Autowired
	LeaveDAO leaveDAO;
	
	//////////*職位*///////////
	@GetMapping("/leaves")
	public List<Leave> getAllLeaves(){
		return leaveDAO.findAll();
	}
	
	@GetMapping("/leave")
	public Leave getOneLeave(String id){
		return leaveDAO.findById(Integer.parseInt(id)).get();
	}
	
	@PostMapping("/leave")
	public Leave createLeave(String name, int day){
		Leave leave = new Leave();
		leave.setId(0);
		leave.setName(name);
		leave.setDay(day);
		return leaveDAO.save(leave);
	}
	
	@PutMapping("/leave")
	public Leave updateLeave(int id, String name, int day){
		Leave leave = new Leave();
		leave.setId(0);
		leave.setName(name);
		leave.setDay(day);
		return leaveDAO.save(leave);
	}
	
	@DeleteMapping("/leave")
	public String deletePosition(int id){
		leaveDAO.deleteById(id);
		return "{\"delete\":\"success!\"}";
	}
	
}
