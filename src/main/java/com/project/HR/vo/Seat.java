package com.project.HR.vo;

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
@Table(name="seat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	String content;
	
}
