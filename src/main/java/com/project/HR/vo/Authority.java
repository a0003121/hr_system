package com.project.HR.vo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
	@Id
	// 主鍵由數據庫自動維護(AUTO_INCREMENT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	Integer id;
	@Column(name = "user_id")
	Integer userID;
	String feature;

	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="featureList")
    private List<User> users; 	
}
