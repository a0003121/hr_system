package com.project.HR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.Leave;

public interface LeaveDAO extends JpaRepository<Leave, Integer> {
//	Position findByName(String name);
}
