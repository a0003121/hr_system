package com.project.HR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.Seat;

public interface SeatDAO extends JpaRepository<Seat, Integer> {
//	Position findByName(String name);
}
