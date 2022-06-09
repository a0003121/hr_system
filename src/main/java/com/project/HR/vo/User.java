package com.project.HR.vo;


import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	String password;
	Byte enabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	  name = "authority", 
	  joinColumns = @JoinColumn(name = "user_id"), 
	  inverseJoinColumns = @JoinColumn(name = "id"))
	List<Authority> featureList;
}
