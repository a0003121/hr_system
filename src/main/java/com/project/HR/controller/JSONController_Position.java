package com.project.HR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.HR.dao.PositionDAO;
import com.project.HR.vo.Position;

@RestController
public class JSONController_Position {
	
	@Autowired
	PositionDAO positionDAO;
	
	//////////*職位*///////////
	@GetMapping("/positions")
	public List<Position> getAllPosition(){
		return positionDAO.findAll();
	}
	
	@GetMapping("/position")
	public Position getOnePosition(String id){
		return positionDAO.findById(Integer.parseInt(id)).get();
	}
	
	@PostMapping("/position")
	public Position createPosition(String name, int dept){
		Position position = new Position();
		position.setId(0);
		position.setName(name);
		position.setDeptId(dept);
		return positionDAO.save(position);
	}
	
	@PutMapping("/position")
	public Position updatePosition(int id, String name, int dept){
		Position position = new Position();
		position.setId(id);
		position.setName(name);
		position.setDeptId(dept);
		return positionDAO.save(position);
	}
	
	@DeleteMapping("/position")
	public String deletePosition(int id){
		Position position = new Position();
		position.setId(id);
		positionDAO.deleteById(id);
		return "{\"delete\":\"success!\"}";
	}
	
}
