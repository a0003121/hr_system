package com.project.HR.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="`leave`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	Integer day;
	@Column(name="salary_count")
	Float salaryCount;
	
	
}
