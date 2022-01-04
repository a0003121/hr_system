package com.project.HR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.User;

public interface UserDAO extends JpaRepository<User, Integer>{
	User findByName(String name);
}
