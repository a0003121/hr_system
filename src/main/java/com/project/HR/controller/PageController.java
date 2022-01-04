package com.project.HR.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	// Login form  
    @RequestMapping("/login")  
    public String login() {  
        return "login.html";  
    } 
    
	@GetMapping("/employee.do")
	public String gotoEmployeePage(HttpServletRequest request, HttpServletResponse response) {
		return "employee.html";
	}
	
	@GetMapping("/dept.do")
	public String gotoDeptPage() {
		return "dept.html";
	}
	
	@GetMapping("/position.do")
	public String gotoPositionPage() {
		return "position.html";
	}
	
	@GetMapping("/insurance.do")
	public String gotoInsurancePage() {
		return "insurance.html";
	}
}
