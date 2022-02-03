package com.project.HR.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.Calendar;

public interface CalendarDAO extends JpaRepository<Calendar, Integer> {
	List<Calendar> findByDateBetween(Date start, Date end, Sort sort);
	Calendar findByDate(Date date);
}
