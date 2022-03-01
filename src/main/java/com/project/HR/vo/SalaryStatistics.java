package com.project.HR.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="salarystatistics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryStatistics {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(name = "info_id")
	Integer infoId;
	@Column(name = "emp_no")
	Integer empNo;
	@Column(name = "rough_salary")
	Integer roughSalary;
	@Column(name = "leave_count")
	Integer leaveCount;
	@Column(name = "work_insurance")
	Integer workInsurance;
	@Column(name = "health_insurance")
	Integer healthInsurance;
	@Column(name = "salary_result")
	Integer salaryResult;
}
