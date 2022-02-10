package com.project.HR.controller;

import com.project.HR.dao.PositionDAO;
import com.project.HR.vo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class API_Position {
	
	@Autowired
	PositionDAO positionDAO;
	
	//////////*職位*///////////
	@GetMapping("/positions")
	public ResponseEntity<List<Position>> getAllPosition(){
		return ResponseEntity.ok(positionDAO.findAll());
	}
	
	@GetMapping("/position")
	public ResponseEntity<Position> getOnePosition(String id){
		return ResponseEntity.ok(positionDAO.findById(Integer.parseInt(id)).get());
	}
	
	@PostMapping("/position")
	public ResponseEntity<Position> createPosition(String name, int dept){
		Position position = new Position();
		position.setId(0);
		position.setName(name);
		position.setDeptId(dept);
		return ResponseEntity.ok(positionDAO.save(position));
	}
	
	@PutMapping("/position")
	public ResponseEntity<Position> updatePosition(int id, String name, int dept){
		Position position = new Position();
		position.setId(id);
		position.setName(name);
		position.setDeptId(dept);
		return ResponseEntity.ok(positionDAO.save(position));
	}
	
	@DeleteMapping("/position")
	public ResponseEntity<String> deletePosition(int id){
		Position position = new Position();
		position.setId(id);
		positionDAO.deleteById(id);
		return ResponseEntity.ok("{\"delete\":\"success!\"}");
	}
	
}
