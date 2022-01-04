package com.project.HR.vo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="position")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Position {
	@Id
	// 主鍵由數據庫自動維護(AUTO_INCREMENT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="position_id")
	Integer id;
	
	@Column(name = "position_name")
	String name;
	
	@Column(name="dept_id")
	Integer deptId;
	
	@OneToMany(mappedBy = "positionName")
//	@JsonManagedReference
	@JsonIgnore
	List<Employee> employeeList;
	
	@ManyToOne
	@JoinColumn(name="dept_id", insertable = false, updatable = false)
//	@JsonBackReference
	Dept deptName;
}
