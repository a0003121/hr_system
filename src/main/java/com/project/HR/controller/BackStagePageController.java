package com.project.HR.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public String gotoEmployeePage(HttpServletRequest request, HttpServletResponse response) {
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
	
	@GetMapping("/seating.do")
	public String gotoSeatingPage() {
		return "/admin/seating";
	}
	
	@GetMapping("/calendar.do")
	public String gotoCalendarPage() {
		return "/admin/calendar";
	}
	
}
