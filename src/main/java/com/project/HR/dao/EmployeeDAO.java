package com.project.HR.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Integer>{
//	// 使用自動化命名規則進行條件搜尋
//    Employee findByName(String name);
//
//    // 使用自動化命名規則進行條件搜尋(多條件)
//    List<Employee> findByNameAndPosition(String name, String position);
//
//    // 自定義SQL查詢
//    @Query(value = "select * from employee where name = ?1", nativeQuery = true)
//    Employee queryByName(String name);
	List<Employee> findAllByOrderByEmpNoAsc();
}
