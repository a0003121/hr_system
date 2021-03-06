package com.project.HR.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.HR.security.MyUserDetails;

@Controller
public class RootPageController {
	
	// Login form  
    @RequestMapping("/login")  
    public String login() {  
        return "login";  
    } 
    
    // Login form  
    @RequestMapping("/login-error")  
    public String loginError() {  
        return "login-error";  
    }

    @RequestMapping("/user_index")
    public String userIndex() {
        return "/user/index";
    }

    @GetMapping("/role")
    public String defaultAfterLogin(Authentication authentication) {
     MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
     
     for (GrantedAuthority grantedAuth : userDetails.getAuthorities()) {
         if (grantedAuth.getAuthority().contains("ADMIN")) {
        	 return "/admin/index";
         }
     }

     return "/user/index";
  }
}
