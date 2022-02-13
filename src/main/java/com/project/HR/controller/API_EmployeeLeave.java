package com.project.HR.controller;

import com.project.HR.dao.EmployeeLeaveDAO;
import com.project.HR.service.LeaveService;
import com.project.HR.util.Constant;
import com.project.HR.vo.EmployeeLeave;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class API_EmployeeLeave {

    @Autowired
    EmployeeLeaveDAO employeeLeaveDAO;

    @Autowired
    LeaveService leaveService;

    ////////// *員工休假*///////////
    @GetMapping("/employeeLeaves")
    public  ResponseEntity<String> getAllEmployeeLeaves() {
        List<EmployeeLeave> list = employeeLeaveDAO.findAll();
        JSONArray jsonArray = new JSONArray();
        for (EmployeeLeave employeeLeave : list) {
            JSONObject object = new JSONObject(employeeLeave);
            switch (employeeLeave.getStatus()) {
                case Constant.LEAVE_HRADD -> object.put("status", "系統新增");
                case Constant.LEAVE_UNCOMPLETE -> object.put("status", "未完成");
                case Constant.LEAVE_COMPLETED -> object.put("status", "已完成");
            }
            jsonArray.put(object);
        }
        return ResponseEntity.ok(jsonArray.toString());
    }

    @GetMapping("/employeeLeave")
    public  ResponseEntity<String> getOneEmployeeLeave(String id) {
        EmployeeLeave employeeLeave = employeeLeaveDAO.findById(Integer.parseInt(id)).get();
        JSONObject object = new JSONObject(employeeLeave);
        switch (employeeLeave.getStatus()) {
            case Constant.LEAVE_HRADD -> object.put("status", "系統新增");
            case Constant.LEAVE_UNCOMPLETE -> object.put("status", "未完成");
            case Constant.LEAVE_COMPLETED -> object.put("status", "已完成");
        }
        return ResponseEntity.ok(object.toString());
    }

    @PostMapping("/employeeLeave")
    public  ResponseEntity<String> createEmployeeLeave(int status, int employeeId, int leaveId, String date, float hours, long startTime, long endTime) throws ParseException {
        //檢查有沒有重複
        List<EmployeeLeave> checkList = employeeLeaveDAO.findByEmployeeIdAndLeaveDate(employeeId, new SimpleDateFormat("yyyy-MM-dd").parse(date));
        for(EmployeeLeave employeeLeave:checkList){
            long startCheck = employeeLeave.getStartTime().getTime();
            long endCheck = employeeLeave.getEndTime().getTime();
            if ((startTime < startCheck && endTime > startCheck) ||(startCheck < startTime && endCheck > startTime)) { //請假時間重複
                JSONObject obj = new JSONObject();
                obj.put("repeat", true);
                return ResponseEntity
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .body(obj.toString());
            }
        }
        EmployeeLeave employeeLeave = new EmployeeLeave();
        employeeLeave.setId(0);
        employeeLeave.setEmployeeId(employeeId);
        employeeLeave.setLeaveId(leaveId);
        employeeLeave.setHours(hours);
        employeeLeave.setStatus(status);
        employeeLeave.setLeaveDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        employeeLeave.setStartTime(new Timestamp(startTime));
        employeeLeave.setEndTime(new Timestamp(endTime));
        return ResponseEntity.ok(new JSONObject(employeeLeaveDAO.save(employeeLeave)).toString());
    }

    @PutMapping("/employeeLeave")
    public  ResponseEntity<String> updateEmployeeLeave(int id, int leaveId, float hours, String date, long startTime, long endTime) throws ParseException {
        EmployeeLeave employeeLeave = employeeLeaveDAO.findById(id).get();
        //檢查有沒有重複
        List<EmployeeLeave> checkList = employeeLeaveDAO.findByEmployeeIdAndLeaveDate(employeeLeave.getEmployeeId(), new SimpleDateFormat("yyyy-MM-dd").parse(date));
        for(EmployeeLeave employeeLeave1:checkList){
            long startCheck = employeeLeave1.getStartTime().getTime();
            long endCheck = employeeLeave1.getEndTime().getTime();

            if(employeeLeave1.getId() != employeeLeave.getId()){
                if ((startTime < startCheck && endTime > startCheck) ||(startCheck < startTime && endCheck > startTime)) { //請假時間重複
                    JSONObject obj = new JSONObject();
                    obj.put("repeat", true);
                    return ResponseEntity
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .body(obj.toString());
                }
            }
        }

        employeeLeave.setLeaveId(leaveId);
        employeeLeave.setHours(hours);
        employeeLeave.setLeaveDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        employeeLeave.setStartTime(new Timestamp(startTime));
        employeeLeave.setEndTime(new Timestamp(endTime));
        employeeLeave = employeeLeaveDAO.save(employeeLeave);
        return ResponseEntity.ok(new JSONObject(employeeLeaveDAO.save(employeeLeave)).toString());
    }

    @DeleteMapping("/employeeLeaves")
    public  ResponseEntity<String> deleteEmployeeLeave(int id) {
        employeeLeaveDAO.deleteById(id);
        return ResponseEntity.ok("{\"delete\":\"success!\"}");
    }

    // 取得請假天數
    @GetMapping("/leaveDays")
    public String getleaveDays(Long startTime, Long endTime) {
        return Double.toString(leaveService.getWorkingHour(startTime, endTime));
    }

}
