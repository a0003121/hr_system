package com.project.HR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.EmployeeLeave;

public interface EmployeeLeaveDAO extends JpaRepository<EmployeeLeave, Integer> {

}
