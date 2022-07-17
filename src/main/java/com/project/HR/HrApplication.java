package com.project.HR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //允許排程
public class HrApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}
	
}
