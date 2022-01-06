package com.project.HR.vo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="employee")
@EntityListeners(AuditingEntityListener.class)
//啟用審計(Auditing)的實體中都必須註冊這個 Listener，否則是沒有效果
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@Id
	 // 主鍵由數據庫自動維護(AUTO_INCREMENT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_id")
	Integer id;
	
	String name;
	Integer position;
	Integer dept;
	@Column(name="emp_no")
	Integer empNo;
	@Column(name="hire_date")
	Date hireDate;
	Integer salary;
	@Column(name="health_insurance")
	Integer healthInsurance;
	@Column(name="work_insurance")
	Integer workInsurance;
	Integer compensation;
	
	@ManyToOne
	@JoinColumn(name="position", insertable = false, updatable = false)
//	@JsonBackReference
	Position positionName;
	
	@ManyToOne
	@JoinColumn(name="dept", insertable = false, updatable = false)
//	@JsonBackReference
	Dept deptName;
}