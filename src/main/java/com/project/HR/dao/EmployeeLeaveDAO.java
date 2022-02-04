package com.project.HR.dao;

import com.project.HR.vo.EmployeeLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EmployeeLeaveDAO extends JpaRepository<EmployeeLeave, Integer> {
    List<EmployeeLeave> findByEmployeeId(Integer employeeId);
    List<EmployeeLeave> findByEmployeeIdAndLeaveDate(Integer employeeId, Date leaveDate);
}
