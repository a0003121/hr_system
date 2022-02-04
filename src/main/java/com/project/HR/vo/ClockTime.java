package com.project.HR.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="clocktime")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClockTime {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(name="clock_date")
	Date clockDate;
	@Column(name="emp_no")
	Integer empNo;
	@Column(name="start_time")
	Timestamp startTime;
	@Column(name="end_time")
	Timestamp endTime;
	@Column(name="start_id")
	Integer startId;
	@Column(name="end_id")
	Integer endId;
	Integer status;
}
