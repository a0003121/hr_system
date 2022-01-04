package com.project.HR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.Dept;

public interface DeptDAO extends JpaRepository<Dept, Integer> {
	// 使用自動化命名規則進行條件搜尋
//    Dept findByName(String name);


}
