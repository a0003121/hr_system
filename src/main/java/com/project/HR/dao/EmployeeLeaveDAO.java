package com.project.HR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.EmployeeLeave;

import java.util.List;

public interface EmployeeLeaveDAO extends JpaRepository<EmployeeLeave, Integer> {
    List<EmployeeLeave> findByEmployeeId(Integer employeeId);
}
