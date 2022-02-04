package com.project.HR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //允許排程
public class HrApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}
	
}
