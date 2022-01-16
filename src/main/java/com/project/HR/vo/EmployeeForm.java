package com.project.HR.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeForm {
	int id;
	String name;
	int position;
	int empNo;
	int dept;
	Date hireDate;
	String salary;
}
