package com.project.HR.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.HR.security.MyUserDetails;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Controller
public class PageController {
	// Login form  
    @RequestMapping("/login")  
    public String login() {  
        return "login.html";  
    } 
    
    @PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/employee.do")
	public String gotoEmployeePage(HttpServletRequest request, HttpServletResponse response) {
		return "employee.html";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/dept.do")
	public String gotoDeptPage() {
		return "dept.html";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/position.do")
	public String gotoPositionPage() {
		return "position.html";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/insurance.do")
	public String gotoInsurancePage() {
		return "insurance.html";
	}
	
	@GetMapping("/role")
    public String defaultAfterLogin(Authentication authentication) {
     MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
     
     for (GrantedAuthority grantedAuth : userDetails.getAuthorities()) {
         if (grantedAuth.getAuthority().contains("ADMIN")) {
        	 return "index.html";
         }
     }

     return "user.html";
  }
}
