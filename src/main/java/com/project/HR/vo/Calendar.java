package com.project.HR.vo;

import java.sql.Date;

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
@Table(name="calendar")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	Date date;
	Integer type;
	String note;
}
