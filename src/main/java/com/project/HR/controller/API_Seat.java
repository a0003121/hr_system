package com.project.HR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.HR.dao.SeatDAO;
import com.project.HR.vo.Seat;

@RestController
public class API_Seat {
	@Autowired
	SeatDAO seatDAO;
	
	//////////*座位表*///////////
	@GetMapping("/seats")
	public List<Seat> getAllSeats(){
		return seatDAO.findAll();
	}
	
	@GetMapping("/seat")
	public Seat getOneSeat(String id){
		return seatDAO.findById(Integer.parseInt(id)).get();
	}
	
	@PostMapping("/seat")
	public Seat createSeat (String content) {
		Seat seat = new Seat();
		seat.setId(0);
		seat.setContent(content);
		return seatDAO.save(seat);
	}
	
	@PutMapping("/seat")
	public Seat updateSeat(int id, String content) {
		Seat seat = new Seat();
		seat.setId(id);
		seat.setContent(content);
		return seatDAO.save(seat);
	}
	
	@DeleteMapping("/seat")
	public String deleteSeat(String id) {
		seatDAO.deleteById(Integer.parseInt(id));
		return "{\"delete\":\"success!\"}";
	}
	
}
