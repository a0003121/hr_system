package com.project.HR.controller;

import com.project.HR.dao.*;
import com.project.HR.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
public class API_Salary {
    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    EmployeeLeaveDAO employeeLeaveDAO;
    @Autowired
    LeaveDAO leaveDAO;
    @Autowired
    SalaryInfoDAO salaryInfoDAO;
    @Autowired
    SalaryStatisticsDAO salaryStatisticsDAO;
    //////////*薪資計算*///////////

    //取得薪資計算區間員工名單
    @GetMapping("/salary_member")
    public ResponseEntity<List<Employee>> getAllSalaryCalculateMember(Long startDate, Long endDate) {
        return ResponseEntity.ok(employeeDAO.findSalaryMemberByHireDateAndLeaveDate(new Date(startDate), new Date(endDate)));
    }

    @GetMapping("/salaryInfo")
    public ResponseEntity<List<SalaryInfo>> getAllSalaryInfo() {
        return ResponseEntity.ok(salaryInfoDAO.findAll());
    }

    @GetMapping("/salaryStatistics")
    public ResponseEntity<List<SalaryStatistics>> getSalaryStatisticsbyInfoId(int infoId) {
        return ResponseEntity.ok(salaryStatisticsDAO.findByInfoId(infoId));
    }

    @Transactional
    @DeleteMapping("/salaryInfo")
    public ResponseEntity<String> deleteSalaryInfo(int id) {
        salaryStatisticsDAO.deleteByInfoId(id);
        salaryInfoDAO.deleteById(id);
        return ResponseEntity.ok("{\"delete\":\"success!\"}");
    }

    //薪資計算
    @Transactional
    @GetMapping("/perform_cal")
    public ResponseEntity<SalaryInfo> performCal(Long startDate, Long endDate, @RequestParam(value = "ids[]") String[] ids, String name) {
        //取得計算天數
        java.util.Calendar start = java.util.Calendar.getInstance();
        start.setTimeInMillis(startDate);
        java.util.Calendar end = java.util.Calendar.getInstance();
        end.setTimeInMillis(endDate);
        LocalDate localDateStart = LocalDate.of(start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DAY_OF_MONTH));
        LocalDate localDateEnd = LocalDate.of(end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DAY_OF_MONTH));
        long daysBetween = DAYS.between(localDateStart, localDateEnd) + 1L;

        //新增資料到salary info
        SalaryInfo salaryInfo = new SalaryInfo(0, new Timestamp(startDate), new Timestamp(endDate), name);
        salaryInfo = salaryInfoDAO.save(salaryInfo);

        //新增資料到salary statistics
        List<Leave> leaveReference = leaveDAO.findAll();
        Map<Integer, Float> referenceMap = leaveReference.stream().collect(Collectors.toMap(Leave::getId, Leave::getSalaryCount));
        for (String id : ids) {

            //員工資料
            Employee employee = employeeDAO.getById(Integer.parseInt(id));

            //請假資料
            float leaveTotal = 0;
            List<EmployeeLeave> leaveList = employeeLeaveDAO.findByEmployeeId(employee.getEmpNo());
            for (EmployeeLeave employeeLeave : leaveList) {
                int leaveType = employeeLeave.getLeaveId();
                float leaveHour = employeeLeave.getHours();
                leaveTotal += referenceMap.get(leaveType) * leaveHour;
            }

            //計算最後金額
            int salary = employee.getSalary();
            float calculateResult = salary * (daysBetween / 30f) - leaveTotal;

            //存入資料
            SalaryStatistics salaryStatistics = new SalaryStatistics(0, salaryInfo.getInfoId(), employee.getEmpNo(), Math.round(calculateResult));
            salaryStatisticsDAO.save(salaryStatistics);
        }
        return ResponseEntity.ok(salaryInfo);
    }

}
