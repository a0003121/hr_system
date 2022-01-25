package com.project.HR.dao;

import com.project.HR.vo.SalaryStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryStatisticsDAO extends JpaRepository<SalaryStatistics, Integer> {
    void deleteByInfoId(Integer infoId);
    List<SalaryStatistics> findByInfoId(Integer infoId);
}
