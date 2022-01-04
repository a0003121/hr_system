package com.project.HR.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="dept")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
	@Id
	// 主鍵由數據庫自動維護(AUTO_INCREMENT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dept_id")
	Integer id;
	
	@Column(name = "dept_name")
	String name;
	
	@OneToMany(mappedBy = "deptName")
//	@JsonManagedReference
	@JsonIgnore
	List<Employee> employeeList;
	
	@OneToMany(mappedBy = "deptName")
//	@JsonManagedReference
	@JsonIgnore
	List<Position> positionList;
}
