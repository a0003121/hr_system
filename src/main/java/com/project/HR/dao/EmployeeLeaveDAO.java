package com.project.HR.dao;

import com.project.HR.vo.EmployeeLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

public interface EmployeeLeaveDAO extends JpaRepository<EmployeeLeave, Integer> {
    List<EmployeeLeave> findByEmployeeId(Integer employeeId);
    List<EmployeeLeave> findByEmployeeIdAndLeaveDate(Integer employeeId, Date leaveDate);
    List<EmployeeLeave> findByEmployeeIdAndLeaveDateBetween(Integer employeeId, Date leaveStart, Date leaveEnd);
}
