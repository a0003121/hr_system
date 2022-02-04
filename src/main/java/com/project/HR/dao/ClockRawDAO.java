package com.project.HR.dao;

import com.project.HR.vo.ClockRaw;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ClockRawDAO extends JpaRepository<ClockRaw, Integer> {
    List<ClockRaw> findByTimeBetweenOrderByEmpNoAscTimeAsc(Timestamp start, Timestamp end);
    List<ClockRaw> findByTimeBetweenAndEmpNoOrderByTimeAsc(Timestamp start, Timestamp end, Integer empNo);

}
