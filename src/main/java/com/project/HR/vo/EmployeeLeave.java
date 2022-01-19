package com.project.HR.vo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="employee_leave")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLeave {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name = "employee_id")
	Integer employeeId;
	
	@Column(name = "leave_id")
	Integer leaveId;
	
	@Column(name="start_time")
	Timestamp startTime;
	
	@Column(name="end_time")
	Timestamp endTime;
	
	Float hours;
	
	@ManyToOne
	@JoinColumn(name = "leave_id", insertable = false, updatable = false)
	Leave leave;
	
}
