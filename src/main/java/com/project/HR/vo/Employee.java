package com.project.HR.vo;

import java.sql.Date;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
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
	@Column(name="leave_date")
	Date leaveDate;
	Integer salary;
	@Column(name="health_insurance")
	Integer healthInsurance;
	@Column(name="work_insurance")
	Integer workInsurance;
	Integer compensation;
	Integer status;
	String email;
	String note;
	
	@ManyToOne
	@JoinColumn(name="position", insertable = false, updatable = false)
//	@JsonBackReference
	Position positionName;
	
	@ManyToOne
	@JoinColumn(name="dept", insertable = false, updatable = false)
//	@JsonBackReference
	Dept deptName;
}
