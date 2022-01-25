package com.project.HR.dao;

import com.project.HR.vo.SalaryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryInfoDAO extends JpaRepository<SalaryInfo, Integer> {
}
