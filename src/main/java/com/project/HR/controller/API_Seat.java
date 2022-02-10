package com.project.HR.controller;

import com.project.HR.dao.SeatDAO;
import com.project.HR.vo.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class API_Seat {
    @Autowired
    SeatDAO seatDAO;

    //////////*座位表*///////////
    @GetMapping("/seats")
    public ResponseEntity<List<Seat>> getAllSeats() {
        return ResponseEntity.ok(seatDAO.findAll());
    }

    @GetMapping("/seat")
    public ResponseEntity<Seat> getOneSeat(String id) {
        return ResponseEntity.ok(seatDAO.findById(Integer.parseInt(id)).get());
    }

    @PostMapping("/seat")
    public ResponseEntity<Seat> createSeat(String content) {
        Seat seat = new Seat();
        seat.setId(0);
        seat.setContent(content);
        return ResponseEntity.ok(seatDAO.save(seat));
    }

    @PutMapping("/seat")
    public ResponseEntity<Seat> updateSeat(int id, String content) {
        Seat seat = new Seat();
        seat.setId(id);
        seat.setContent(content);
        return ResponseEntity.ok(seatDAO.save(seat));
    }

    @DeleteMapping("/seat")
    public ResponseEntity<String> deleteSeat(String id) {
        seatDAO.deleteById(Integer.parseInt(id));
        return ResponseEntity.ok("{\"delete\":\"success!\"}");
    }

}
