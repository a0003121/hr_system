package com.project.HR.dao;

import com.project.HR.vo.ClockTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ClockTimeDAO extends JpaRepository<ClockTime, Integer> {
    List<ClockTime> findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Date start, Date end);
    List<ClockTime> findByEmpNoAndClockDateBetween(Integer empNo, Date startDate, Date endDate, Sort sort);
    List<ClockTime> findByEmpNoAndClockDate(Integer empNo, Date clockDate);
    List<ClockTime> findByStartId(Integer startId);
    List<ClockTime> findByEndId(Integer endId);
    void deleteByEmpNoAndClockDate(Integer empNo, Date clockDate);
//    Page<ClockTime> findByClockDateBetween(Date startDate, Date endDate, Pageable pageable);
//    Pageable sortedByDate = PageRequest.of(0, 3, Sort.by("clockDate"));
//    Page<ClockTime> pageResult = clockTimeDAO.findByClockDateBetween(new java.sql.Date(dateStart), new java.sql.Date(dateEnd), sortedByDate);
//        pageResult.getNumberOfElements(); //本頁筆數
//        pageResult.getSize();             // 每頁筆數
//        pageResult.getTotalElements();    // 全部筆數
//        pageResult.getTotalPages();       // 全部頁數
//
//        return pageResult.getContent();
}
