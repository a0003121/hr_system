package com.project.HR.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="salaryinfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryInfo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="info_id")
	Integer infoId;
	@Column(name="calc_date")
	String calcDate;
	@Column(name="start_date")
	Timestamp startDate;
	@Column(name="end_date")
	Timestamp endDate;
	String name;
}
