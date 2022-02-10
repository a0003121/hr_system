package com.project.HR.controller;

import com.project.HR.dao.EmployeeDAO;
import com.project.HR.vo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class API_Employee {
    @Autowired
    EmployeeDAO employeeDAO;

    ////////// *員工*///////////
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {

        return ResponseEntity.ok(employeeDAO.findAllByOrderByEmpNoAsc());
    }

    @GetMapping("/employee")
    public ResponseEntity<Employee> getOneEmployee(int id) {
        return ResponseEntity.ok(employeeDAO.findById(id).get());
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(Employee employeeData, String leaveDateN, String salaryN) throws ParseException {
        if (!"".equals(salaryN.trim())) {
            employeeData.setSalary(Integer.parseInt(salaryN));
        }
        if (!"".equals(leaveDateN.trim())) {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(leaveDateN);
            employeeData.setLeaveDate(new Date(date.getTime()));
        }

        Employee temp = null;
        try {
            temp = employeeDAO.save(employeeData);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new Employee());
        }

        return ResponseEntity.ok(temp);
    }

    @PutMapping("/employee")
    public ResponseEntity<Employee> updateEmployee(Employee employeeData, String leaveDateN, String salaryN) throws ParseException {
        Employee result = null;

        if (!"".equals(salaryN.trim())) {
            employeeData.setSalary(Integer.parseInt(salaryN));
        }
        if (!"".equals(leaveDateN.trim())) {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(leaveDateN);
            employeeData.setLeaveDate(new Date(date.getTime()));
        }
        try {
            result = employeeDAO.save(employeeData);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new Employee());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/employee")
    public ResponseEntity<String> deleteEmployee(int id) {
        employeeDAO.deleteById(id);
        return ResponseEntity.ok("{\"delete\":\"success!\"}");
    }
}