package com.project.HR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.Position;

public interface PositionDAO extends JpaRepository<Position, Integer> {
//	Position findByName(String name);
}
