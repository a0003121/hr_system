package com.project.HR.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasAuthority('ADMIN')")
@Controller
public class BackStagePageController {

    @RequestMapping("/backheader")
    public String header() {
        return "/admin/header";
    }

    @GetMapping("/employee.do")
    public String gotoEmployeePage() {
        return "/admin/employee";
    }

    @GetMapping("/dept.do")
    public String gotoDeptPage() {
        return "/admin/dept";
    }

    @GetMapping("/position.do")
    public String gotoPositionPage() {
        return "/admin/position";
    }

    @GetMapping("/insurance.do")
    public String gotoInsurancePage() {
        return "/admin/insurance";
    }

    @GetMapping("/leave.do")
    public String gotoLeavePage() {
        return "/admin/employee_leave";
    }

    @GetMapping("/seating.do")
    public String gotoSeatingPage() {
        return "/admin/seating";
    }

    @GetMapping("/calendar.do")
    public String gotoCalendarPage() {
        return "/admin/calendar";
    }

    @GetMapping("/salary.do")
    public String gotoSalaryPage() {
        return "/admin/salary";
    }

    @GetMapping("/clock.do")
    public String gotoClockPage() {
        return "/admin/clock";
    }
}
